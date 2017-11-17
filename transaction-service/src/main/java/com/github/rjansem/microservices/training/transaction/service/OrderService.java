package com.github.rjansem.microservices.training.transaction.service;

import com.github.rjansem.microservices.training.transaction.exception.TransactionExceptionFactory;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.transaction.client.OrdreClient;
import com.github.rjansem.microservices.training.transaction.domain.efs.ObjectVide;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.Ordre;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.OrdreDetail;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.SignatureKBV;
import com.github.rjansem.microservices.training.transaction.domain.efs.post.PostTransaction;
import com.github.rjansem.microservices.training.transaction.domain.pbi.AddressBook;
import com.github.rjansem.microservices.training.transaction.domain.pbi.PaymentOrder;
import com.github.rjansem.microservices.training.transaction.domain.pbi.account.TransactionAccount;
import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.ListOfTransaction;
import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.Payment;
import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.SignTransaction;
import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.Transaction;
import com.github.rjansem.microservices.training.transaction.exception.TransactionExceptionFactory;
import com.github.rjansem.microservices.training.transaction.mapper.OrdreDetailToPaymentOrderMapper;
import com.github.rjansem.microservices.training.transaction.mapper.PostPaymentOrderToPostTransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.github.rjansem.microservices.training.transaction.api.ApiConstants.CODE_APPLICATION;

/**
 * Service pour la gestion des ordres
 *
 * @author aazzerrifi
 */
@Service
public class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private static final String TRANSACTIONS_CACHE_NAME = "transactionsCache";

    private static final String ORDRES_CACHE_NAME = "ordresCache";

    private final RetrieveABookService retrieveABookService;

    private final OrdreClient ordreClient;

    private final DeviseService deviseService;

    private PostPaymentOrderToPostTransactionMapper postPaymentOrderToPostTransactionMapper = new PostPaymentOrderToPostTransactionMapper();

    private OrdreDetailToPaymentOrderMapper mapper = new OrdreDetailToPaymentOrderMapper();

    private Cache transactionsCache;

    private final Cache ordresCache;

    private RedisCacheManager redisCacheManager;

    @Autowired
    public OrderService(RetrieveABookService retrieveABookService, OrdreClient ordreClient, DeviseService deviseService, RedisCacheManager redisCacheManager) {
        this.retrieveABookService = retrieveABookService;
        this.ordreClient = ordreClient;
        this.deviseService = deviseService;
        this.redisCacheManager = redisCacheManager;
        transactionsCache = redisCacheManager.getCache(this.TRANSACTIONS_CACHE_NAME);
        ordresCache = redisCacheManager.getCache(this.ORDRES_CACHE_NAME);
    }

    public Observable<ListOfTransaction> signPayment(SignTransaction signTransaction, AuthenticatedUser user) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Signature des ordres de paiement du client {} de l'application {}", login, CODE_APPLICATION);
        SignatureKBV signKBV = mapper.map(signTransaction, login);
        List<Observable<Payment>> collect = signTransaction.getTransactionIds().stream().map(id -> signPaymentByKbv(user, signKBV, id)
                .flatMap(objectVide -> findPaymentOrderById(id, user).map(mapper::map))).collect(Collectors.toList());
        return Observable.zip(collect, objects -> Arrays.stream(objects).map(o -> (Payment) o).collect(Collectors.toList()))
                .map(payments -> new ListOfTransaction(payments));
    }

    public Observable<PaymentOrder> initiateSEPAPaymentOrder(PaymentOrder paymentOrder, AuthenticatedUser user) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        List<Ordre> ordresList = ordresCache.get(user.getLogin(), List.class);

        //On vide les caches lorsqu'on fait une nouvelle transaction afin de prendre en compte cette dernière
        if(ordresList != null) {
            ordresList.stream()
                    .map(ordre -> {
                        transactionsCache.evict(ordre.getId());
                        return ordre;
                    })
                    .collect(Collectors.toList());
            ordresCache.evict(user.getLogin());
        }

        LOGGER.info("Emission d'ordre de paiement du client {} de l'application {}", login, CODE_APPLICATION);
        PostTransaction postTransaction = postPaymentOrderToPostTransactionMapper.map(paymentOrder, login);
        return deviseService.findDeviseTransactionById(user.getToken(), postTransaction.getDevise())
                .map(d -> {
                    postTransaction.setDevise(String.valueOf(d));
                    return postTransaction;
                })
                .flatMap(p -> ordreClient.emissionTransaction(p, user.getToken())
                        .doOnNext(accs -> LOGGER.info("Emission avec avec succès de l'ordre de paiement {} pour du client {}" +
                                " de l'application {}", accs.getIdOrdre(), login, CODE_APPLICATION))
                        .doOnError(err -> TransactionExceptionFactory.wrapNOBCException(err, login))
                        .flatMap(emissionOrdre -> findPaymentOrderById(emissionOrdre.getIdOrdre(), user)));
    }

    private Observable<PaymentOrder> findPaymentOrderById(String id, AuthenticatedUser user) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche d'un ordre de paiement du client {} de l'application {}", login, CODE_APPLICATION);
        return ordreClient.findDetailOrdreById(id, login, CODE_APPLICATION, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération le détail du l'ordre {} pour du client {} de l'application {}",
                        id, login, CODE_APPLICATION))
                .doOnError(err -> TransactionExceptionFactory.wrapNOBCException(err, login))
                .onErrorReturn(throwable -> new OrdreDetail())
                .map(mapper::map).flatMap(paymentOrder -> {
                    PaymentOrder paymentOr = deviseService.getPaymentOrderWithDevise(paymentOrder, user.getToken());
                    return mapAccount(user, paymentOr);
                });
    }

    private Observable<PaymentOrder> mapAccount(AuthenticatedUser user, PaymentOrder paymentOr) {
        TransactionAccount creditAccount = paymentOr.getCreditAccount();
        return retrieveABookService.findAllBeneficiaires(user)
                .map(retrieveABook -> {
                    AddressBook book = retrieveABook.getContent().stream()
                            .filter(addressBook -> addressBook.getIban().equals(creditAccount.getIban())).findFirst().get();
                    return mapper.map(paymentOr, book);
                }).onErrorReturn(throwable -> paymentOr);
    }

    private Observable<ObjectVide> signPaymentByKbv(AuthenticatedUser user, SignatureKBV signKBV, String id) {
        String login = user.getLogin();
        return ordreClient.signeKbv(id, signKBV, user.getToken())
                .doOnNext(accs -> LOGGER.info("Signature avec succès de l'ordre de paiement {} pour le client {} ",id, login))
                .doOnError(err -> TransactionExceptionFactory.wrapNOBCException(err, login));
    }
}
