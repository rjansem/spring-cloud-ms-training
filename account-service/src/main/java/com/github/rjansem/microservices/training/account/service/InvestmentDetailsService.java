package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.client.ClientConstants;
import com.github.rjansem.microservices.training.account.client.CompteClient;
import com.github.rjansem.microservices.training.account.domain.efs.investissement.AssuranceVie;
import com.github.rjansem.microservices.training.account.domain.efs.investissement.PortefeuilleTitres;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.domain.pbi.account.AccountGroup;
import com.github.rjansem.microservices.training.account.mapper.account.AccountsToAccountOverviewMapper;
import com.github.rjansem.microservices.training.account.mapper.account.investissement.AssuranceVieToAccountMapper;
import com.github.rjansem.microservices.training.account.mapper.account.investissement.PortefeuillesTitresToAccountMapper;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.commons.domain.EfsToPibMapper;
import com.github.rjansem.microservices.training.commons.domain.IdentifiableDomain;
import com.github.rjansem.microservices.training.exception.NOBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.List;
import java.util.Objects;

import static com.github.rjansem.microservices.training.account.client.ClientConstants.PP;


/**
 * Services associés à la gestion des investissements
 *
 * @author mbouhamyd
 */
@Service
public class InvestmentDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvestmentDetailsService.class);

    private final CompteClient compteClient;

    @Autowired
    public InvestmentDetailsService(CompteClient compteClient) {
        this.compteClient = compteClient;
    }

    Observable<AccountGroup> findAccountGroupByRacine(AuthenticatedUser user, String codeRacine) {
        AccountsToAccountOverviewMapper mapper = new AccountsToAccountOverviewMapper();
        return findAllAccountsForThisGroupByUser(user, codeRacine).map(mapper::mapInvestments);
    }

    Observable<List<Account>> findAllAccountsForThisGroupByUser(AuthenticatedUser user, String codeRacine) {
        return Observable.merge(
                findAllPortfolioDetailsByIban(user, codeRacine),
                findAllLifeInsuranceByRacine(user, codeRacine)).sorted().toList();
    }

    Observable<Account> findLifeInsuranceById(String numero, AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        return findOneLifeInsuranceExtDetailsByIban(numero, user, codeRacine)
                .first(IdentifiableDomain::hasId);
    }

    Observable<Account> findAllLifeInsuranceByRacine(AuthenticatedUser user, String codeRacine) {
        return findAllLifeInsuranceExtDetailsByIban(user, codeRacine);
    }

    Observable<Account> findAllPortfolioDetailsByIban(AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche des portes feuilles détaillée pour le client {} avec la racine {}", login, codeRacine);
        return compteClient.findPortefeuilleTitresByLoginAndRacine(login, codeRacine, ClientConstants.PP, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération des détails de {} porte(s) feuille(s) pour le client {} avec la racine {}",
                        accs.size(), login, codeRacine))
                .doOnError(e -> {
                    String msg = String.format("Erreur lors de la récupération des portefeuilles détaillés du client '%s' de la racine %s",
                            login, codeRacine);
                    NOBCException.throwEfsError(msg, e);
                })
                .flatMap(Observable::from)
                .flatMap(compte -> findOnePortfolioDetailsByNumero(compte.getNumero(), user, codeRacine));
    }

    Observable<Account> findOnePortfolioDetailsByNumero(String numero, AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche du détail d'un portefeuille pour du client {} avec la racine {}", login, codeRacine);
        EfsToPibMapper<PortefeuilleTitres, Account> mapper = new PortefeuillesTitresToAccountMapper();
        return compteClient.findDetailPortefeuilleTitresByLoginAndRacine(numero, login, codeRacine, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération du détail d'un  porte feuille titres {} pour le client {} avec la racine {}",
                        numero, login, codeRacine))
                .doOnError(e -> {
                    String msg = String.format("Erreur lors de la récupération du détail du portefeuilles '%s' du client %s de la " +
                            "racine %s", numero, login, codeRacine);
                    NOBCException.throwEfsError(msg, e);
                })
                .map(mapper::map)
                .filter(IdentifiableDomain::hasId);
    }

    Observable<Account> findAllLifeInsuranceExtDetailsByIban(AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche des assurances vie (Ext) détaillée pour du client {} avec la racine {}", login, codeRacine);
        return compteClient.findAssuranceVieByLoginAndRacine(login, codeRacine, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération de {} assurance(s) vie (Ext) détaillée pour le client {} avec la racine {}",
                        accs.size(), login, codeRacine))
                .doOnError(e -> {
                    String msg = String.format("Erreur lors de la récupération des assurance vie (ext) du client %s de la racine %s",
                            login, codeRacine);
                    NOBCException.throwEfsError(msg, e);
                })
                .flatMap(Observable::from)
                .flatMap(compte -> findOneLifeInsuranceExtDetailsByIban(compte.getNumero(), user, codeRacine));
    }

    Observable<Account> findOneLifeInsuranceExtDetailsByIban(String numero, AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche le détail de l'assurance vie (Ext){} pour du client {} avec la racine {}",numero, login,
                codeRacine);
        EfsToPibMapper<AssuranceVie, Account> mapper = new AssuranceVieToAccountMapper();
        return compteClient.findDetailAssuranceVieByLoginAndRacine(numero, login, codeRacine, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération du détail de la assurance vie (Ext) {} pour le client {} " +
                        "avec la racine {}", numero, login, codeRacine))
                .doOnError(e -> {
                    String msg = String.format("Erreur lors de la récupération du détail d'une assurance vie (Ext) %s " +
                            "du client %s de la racine %s", numero, login, codeRacine);
                    LOGGER.info("l'assurance {} du client {} de la racine {} n'est pas externe", numero , login, codeRacine);
                    //NOBCException.throwEfsError(msg, e);
                })
                .map(AssuranceVie::setExterneTypeIfMissing)
                .map(mapper::map)
                .onErrorReturn(err -> findOneLifeInsuranceNobcDetailsByIban(numero, user, codeRacine)
                        .toBlocking().first())
                .filter(IdentifiableDomain::hasId);
    }

    Observable<Account> findAllLifeInsuranceNobcDetailsByIban(AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche les assurances vie (NOBC) pour le client {} avec la racine {}", login, codeRacine);
        return compteClient.findAssuranceVieByLoginAndRacine(login, codeRacine, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération de {} assurance(s) vie (NOBC) pour le client {} " +
                                "avec la racine {}",accs.size(), login, codeRacine))
                .doOnError(e -> {
                    String msg = String.format("Erreur lors de la récupération des assurances vie(NOBC) du client %s " +
                                    "de la racine %s",login, codeRacine);
                    NOBCException.throwEfsError(msg, e);
                })
                .flatMap(Observable::from)
                .flatMap(compte -> findOneLifeInsuranceNobcDetailsByIban(compte.getNumero(), user, codeRacine)
                        .onErrorReturn(throwable -> new Account()).filter(IdentifiableDomain::hasId));
    }

    Observable<Account> findOneLifeInsuranceNobcDetailsByIban(String numero, AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche le détail de l'assurance vie (NOBC) {}  du client {} avec la racine {}"
                ,numero, login, codeRacine);
        EfsToPibMapper<AssuranceVie, Account> mapper = new AssuranceVieToAccountMapper();
        return compteClient.findDetailAssuranceVieNobcByLoginAndRacine(numero, login, codeRacine, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération du détail de l'assurance vie (NOBC) {} pour le client {}" +
                        " avec " +"la racine {}", numero, login, codeRacine))
                .doOnError(e -> {
                    String msg = String.format("Erreur lors de la récupération du détail d'une assurance vie %s " +
                            "du client %s " + "de la racine %s", numero, login, codeRacine);
                    NOBCException.throwEfsError(msg, e);
                })
                .map(AssuranceVie::setInterneTypeIfMissing)
                .map(mapper::map)
                .filter(IdentifiableDomain::hasId);

    }
}