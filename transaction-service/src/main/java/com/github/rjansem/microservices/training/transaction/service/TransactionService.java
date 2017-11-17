package com.github.rjansem.microservices.training.transaction.service;

import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.exception.NOBCException;
import com.github.rjansem.microservices.training.transaction.client.OrdreClient;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.Ordre;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.OrdreDetail;
import com.github.rjansem.microservices.training.transaction.domain.pbi.AddressBook;
import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.ListOfTransaction;
import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.Transaction;
import com.github.rjansem.microservices.training.transaction.mapper.OrdreDetailToTransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service pour la gestion des transactions account
 *
 * @author mbouhamyd
 */
@Service
public class TransactionService {

    static final String CODE_APPLICATION = "WBSCT3";

    static final String CODE_APPLICATION_RECURRING = "WBSCTP";

    static final String CODE_APPLICATION_INTERNATIONAL = "WBVE";

    static final String PAYEMENT_MODE_RECURRING = "RECURRING";

    static final String PAYEMENT_MODE_SINGLE = "SINGLE";

    static final String PAYEMENT_STATUS_ARCHIVE_INTEGRE = "1";

    static final String PAYEMENT_STATUS_NON_COMPABILISE = "2";

    static final String PAYEMENT_PRMANENT_EXTERN = "PERE";

    private static final String ORDRES_CACHE_NAME = "ordresCache";

    private static final String TRANSACTIONS_CACHE_NAME = "transactionsCache";

    private static final String PROFIL_RACINE_PARTICULIER = "P";

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

    private final OrdreClient ordreClient;

    private final RetrieveABookService retrieveABookService;

    private final Cache ordresCache;

    private final Cache transactionsCache;


    @Autowired
    public TransactionService(OrdreClient ordreClient, RetrieveABookService retrieveABookService, RedisCacheManager redisCacheManager) {
        this.ordreClient = ordreClient;
        this.retrieveABookService = retrieveABookService;
        ordresCache = redisCacheManager.getCache(this.ORDRES_CACHE_NAME);
        transactionsCache = redisCacheManager.getCache(this.TRANSACTIONS_CACHE_NAME);
    }

    public List<Ordre> findAllOrdres(AuthenticatedUser user) {
        List<Ordre> ordresList = ordresCache.get(user.getLogin(), List.class);

        if(ordresList == null) {
            Observable<Ordre> recurring = findAllRecurringTransactions(user);
            Observable<Ordre> single = findAllSingleTransactions(user);
            Observable<Ordre> international = findAllInternationalTransactions(user);

            ordresList = Observable.merge(recurring, single, international)
                    .filter(ordre -> ordre.getCompteEmetteur().getProfil().equals(PROFIL_RACINE_PARTICULIER))
                    .toSortedList()
                    .toBlocking()
                    .single()
                    .stream()
                    .sorted()
                    .collect(Collectors.toList());

            cacheOrdres(user.getLogin(), ordresList);
        }

        return ordresList;
    }

    public Observable<List<Transaction>> findTransactions(AuthenticatedUser user, List<Ordre> ordres) {
        Observable<List<Ordre>> l = Observable.just(ordres);
        Observable<List<AddressBook>> beneficiaires = retrieveABookService.findAllBeneficiaires(user)
                .map(content -> content.getContent());

        return beneficiaires.flatMap(addressBooks -> {
            return l.flatMap(Observable::from)
                    .flatMap(ordre -> {
                        return findDetailOrdreById(ordre.getId(), user, ordre.getId().startsWith(CODE_APPLICATION_RECURRING) ? PAYEMENT_MODE_RECURRING : PAYEMENT_MODE_SINGLE, addressBooks);
                    }).toList();
        });
    }

    private void cacheOrdres(String login, List<Ordre> ordres) {
        LOGGER.info("Ajout des ordres de l'utilisateur {} au cache", login);
        ordresCache.put(login, ordres);
    }

    private void cacheOrdresDetail(String key, Transaction transaction) {
        LOGGER.info("Ajout de la transaction d'id {} au cache", key);
        transactionsCache.put(key, transaction);
    }

    public Observable<Ordre> findAllInternationalTransactions(AuthenticatedUser user) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche les ordes internationaux du client {} de l'application {}", login, CODE_APPLICATION);
        return ordreClient.findAllOrdres(login, CODE_APPLICATION_INTERNATIONAL, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération de {} ordr(s) pour le client {} de l'application {}",
                        accs.size(), login, CODE_APPLICATION_INTERNATIONAL))
                .doOnError(err -> {
                    String msg = String.format("erreur lors de la récupération des odres du  %s de l'application %s", login , CODE_APPLICATION_INTERNATIONAL);
                    NOBCException.throwEfsError(msg, err);
                })
        .flatMap(Observable::from)
        .filter(ordre -> ordre.getStatut().equals(PAYEMENT_STATUS_ARCHIVE_INTEGRE) || ordre.getStatut().equals(PAYEMENT_STATUS_NON_COMPABILISE))
        ;
    }

    public Observable<Ordre> findAllRecurringTransactions(AuthenticatedUser user) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche les ordes permanents du client {} de l'application {} ", login, CODE_APPLICATION);
        return ordreClient.findAllOrdres(login, CODE_APPLICATION_RECURRING, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération de {} ordr(s) pour le client {} de l'application {}",
                        accs.size(), login, CODE_APPLICATION_RECURRING))
                .doOnError(err -> {
                    String msg = String.format("erreur lors de la récupération des odres du  %s de l'application %s", login, CODE_APPLICATION_RECURRING);
                    NOBCException.throwEfsError(msg, err);
                })
                .flatMap(Observable::from)
                .filter(ordre -> ordre.getStatut().equals(PAYEMENT_STATUS_ARCHIVE_INTEGRE) || ordre.getStatut().equals(PAYEMENT_STATUS_NON_COMPABILISE))
                 ;
    }

    public Observable<Ordre> findAllSingleTransactions(AuthenticatedUser user) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche les ordes du client {} de l'application ", login, CODE_APPLICATION);
        return ordreClient.findAllOrdres(login, CODE_APPLICATION, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération de {} ordr(s) pour le client {} de l'application {}",
                        accs.size(), login, CODE_APPLICATION))
                .doOnError(err -> {
                    String msg = String.format("erreur lors de la récupération des odres du  %s de l'application %s", login, CODE_APPLICATION);
                    NOBCException.throwEfsError(msg, err);
                })
                .flatMap(Observable::from)
                .filter(ordre -> ordre.getStatut().equals(PAYEMENT_STATUS_ARCHIVE_INTEGRE) || ordre.getStatut().equals(PAYEMENT_STATUS_NON_COMPABILISE))
                ;
    }

    public Observable<Transaction> findDetailOrdreById(String id, AuthenticatedUser user, String paymentMode,
                                                       List<AddressBook> beneficiaires){
        Objects.requireNonNull(user);
        String login = Objects.requireNonNull(user.getLogin());
        LOGGER.info("Recherche des transactions pour l'utilisateur {}", login);
        return transactionsFromCache(id).switchIfEmpty(transactionsFromEfs(id, user, paymentMode, beneficiaires));
    }

    private Observable<Transaction> transactionsFromCache(String id) {
        return Optional.ofNullable(transactionsCache.get(id, Transaction.class))
                .map(r -> createObs(r, id))
                .orElse(Observable.empty());
    }

    private Observable<Transaction> createObs(Transaction transaction, String login) {
        return Observable.just((Transaction) transaction);
    }

    public Observable<Transaction> transactionsFromEfs(String id, AuthenticatedUser user, String paymentMode, List<AddressBook> beneficiaires) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(id);
        String login = user.getLogin();
        LOGGER.info("Recherche le détail du l'ordre {} pour le client {} de l'application  ", id, login);
        return ordreClient.findDetailOrdreById(id, login, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération le détail du l'ordre {} pour du client {}",
                        id, login))

                .doOnError(err -> {
                    String msg = String.format("erreur lors de la récupération du détail de l'ordre %s du client %s", id, login);
                    NOBCException.throwEfsError(msg, err);
                })
                .flatMap(ordreDetail -> findBeneficiary(beneficiaires, ordreDetail, paymentMode))
                .doOnNext(transaction -> cacheOrdresDetail(transaction.getId(), transaction));
    }

    public Observable<Transaction> findBeneficiary(List<AddressBook> beneficiaires, OrdreDetail ordreDetail, String paymentMode) {
        OrdreDetailToTransactionMapper mapper = new OrdreDetailToTransactionMapper();
        if (!ordreDetail.getCodeOperation().isEmpty() && !ordreDetail.getCodeOperation().equals(PAYEMENT_PRMANENT_EXTERN)) {
            AddressBook bene = beneficiaires.stream().filter(ben -> ben.getId().equals(ordreDetail.getBeneficiaires().stream().findFirst().get().getIban()))
                    .findFirst().orElse(new AddressBook());
            Transaction transaction = mapper.map(ordreDetail, paymentMode, bene);
            return Observable.just(transaction);
        } else {
            Transaction transaction = mapper.map(ordreDetail, paymentMode, null);
            return Observable.just(transaction);
        }
    }
}
