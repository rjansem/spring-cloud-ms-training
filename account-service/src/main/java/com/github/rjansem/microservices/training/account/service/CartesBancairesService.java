package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.client.CarteBancaireClient;
import com.github.rjansem.microservices.training.account.domain.efs.cartebancaire.CarteBancaire;
import com.github.rjansem.microservices.training.account.domain.pbi.account.RetrieveBalance;
import com.github.rjansem.microservices.training.account.domain.pbi.cartebancaire.CreditCard;
import com.github.rjansem.microservices.training.account.mapper.account.AccountToRetrieveBalanceMapper;
import com.github.rjansem.microservices.training.account.mapper.creditcard.CarteBancaireToCreditCardMapper;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.exception.NOBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Service pour la gestion des cartes bancaires
 *
 * @author jntakpe
 * @author mbouhamyd
 */
@Service
public class CartesBancairesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartesBancairesService.class);

    private final CarteBancaireClient carteBancaireClient;

    private final CheckingAndSavingDetailsService checkingAndSavingDetailsService;

    private final DeviseService deviseService;

    @Autowired
    public CartesBancairesService(CarteBancaireClient carteBancaireClient,
                                  CheckingAndSavingDetailsService checkingAndSavingDetailsService,
                                  DeviseService deviseService) {
        this.carteBancaireClient = carteBancaireClient;
        this.checkingAndSavingDetailsService = checkingAndSavingDetailsService;
        this.deviseService = deviseService;
    }

    public Observable<RetrieveBalance> findRetrieveBalanceByIban(String iban, AuthenticatedUser user, String codeRacine) {
        return findAllCCWithDetailByIban(iban, codeRacine, user).map(creditCards -> new RetrieveBalance().setCreditCards(creditCards));
    }

    public Observable<RetrieveBalance> findRetrieveBalanceById(String accountId, String idCard, String codeRacine, AuthenticatedUser user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(accountId);
        Objects.requireNonNull(idCard);
        return findCCWithDetailById(accountId, idCard, codeRacine, user)
                .toList()
                .map(HashSet::new)
                .map(creditCards -> new RetrieveBalance().setCreditCards(creditCards));
    }

    Observable<CarteBancaire> findDetailCBById(String idCard, String codeRacine, AuthenticatedUser user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(idCard);
        String login = Objects.requireNonNull(user.getLogin());
        LOGGER.info("Recherche le detail de la carte bancaire {} pour l'utilisateur {}", idCard, login);
        return carteBancaireClient.findCarteBancaireDetailById(idCard, login, codeRacine, user.getToken())
                .doOnNext(c -> LOGGER.info("Récupération du détail de la carte {} {}", idCard, c))
                .doOnError(e -> {
                    String msg = String.format("Impossible de récupérer le detail de la carte bancaire id : '%s'", idCard);
                    NOBCException.throwEfsError(msg, e);
                })
                .flatMap(creditCard -> addCurrencyToCB(creditCard, user));
    }

    Observable<CreditCard> findCCWithDetailById(String accountId, String idCard, String codeRacine, AuthenticatedUser user) {
        Objects.requireNonNull(accountId);
        Objects.requireNonNull(user);
        Objects.requireNonNull(idCard);
        String login = Objects.requireNonNull(user.getLogin());
        LOGGER.info("Recherche le detail de la carte bancaire {} pour l'utilisateur {}  ", idCard, login);
        CarteBancaireToCreditCardMapper mapper = new CarteBancaireToCreditCardMapper();
        return carteBancaireClient.findCarteBancaireDetailById(idCard, login, codeRacine, user.getToken())
                .doOnNext(c -> LOGGER.info("Récupération du détail de la carte {} {}", idCard, c))
                .doOnError(e -> {
                    String msg = String.format("Erreur lors de la récupération du détail d'une CB avec l'id '%s'", idCard);
                    NOBCException.throwEfsError(msg, e);
                })
                .map(mapper::map)
                .flatMap(c -> addCurrencyToCC(c, user))
                .flatMap(c -> addRetrieveDateToCarteBancaire(accountId, codeRacine, user, c));

    }

    Observable<Set<CarteBancaire>> findAllCBWithDetailsByIban(String iban, String codeRacine, AuthenticatedUser user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(iban);
        LOGGER.info("Recherche les cartes bancaires du  compte {}", iban);
        return findAllCBByIban(iban, codeRacine, user)
                .flatMap(Observable::from)
                .flatMap(c -> findDetailCBById(String.valueOf(c.getId()), codeRacine, user))
                .toList()
                .map(HashSet::new);
    }

    Observable<Set<CreditCard>> findAllCCWithDetailByIban(String iban, String codeRacine, AuthenticatedUser user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(iban);
        LOGGER.info("Recherche les cartes bancaires du  compte {}", iban);
        return findAllCBByIban(iban, codeRacine, user)
                .flatMap(Observable::from)
                .flatMap(c -> findCCWithDetailById(iban, String.valueOf(c.getId()), codeRacine, user))
                .flatMap(c -> addRetrieveDateToCarteBancaire(iban, codeRacine, user, c))
                .toList()
                .map(HashSet::new);
    }

    private Observable<Set<CarteBancaire>> findAllCBByIban(String iban, String codeRacine, AuthenticatedUser user) {
        return carteBancaireClient.findCarteBancaireByIban(iban, user.getLogin(), codeRacine, user.getToken())
                .doOnNext(cards -> LOGGER.info("Récupération des cartes bancaires pour l'iban {}", iban))
                .doOnError(e -> {
                    String msg = String.format("Erreur lors de la récupération du détail d'une CB avec l'iban %s", iban);
                    NOBCException.throwEfsError(msg, e);
                });
    }

    private Observable<RetrieveBalance> findRetrieveBalance(String accountId, String codeRacine, AuthenticatedUser user) {
        return checkingAndSavingDetailsService.findOneChekingDetailsByIban(accountId, user, codeRacine)
                .map(new AccountToRetrieveBalanceMapper()::map);
    }

    private Observable<CreditCard> addRetrieveDateToCarteBancaire(String accountId,
                                                                  String codeRacine,
                                                                  AuthenticatedUser user,
                                                                  CreditCard creditCredit) {
        return findRetrieveBalance(accountId, codeRacine, user)
                .map(b -> creditCredit.setCardBalanceDate(b.getCreditcardTotalBalanceDate()));
    }

    private Observable<CreditCard> addCurrencyToCC(CreditCard creditCard, AuthenticatedUser user) {
        return deviseService.codeDeviseToCurrency(creditCard.getCardCurrency(), user)
                .map(creditCard::setCardCurrency);
    }

    private Observable<CarteBancaire> addCurrencyToCB(CarteBancaire creditCard, AuthenticatedUser user) {
        return deviseService.codeDeviseToCurrency(creditCard.getDevise().toString(), user)
                .map(creditCard::setLibelleDevise);
    }
}
