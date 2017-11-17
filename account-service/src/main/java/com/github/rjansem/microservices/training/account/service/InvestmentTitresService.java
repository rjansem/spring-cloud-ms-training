package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.client.InvestissementClient;
import com.github.rjansem.microservices.training.account.domain.efs.investissement.InvestissementTitre;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.exception.NOBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.Objects;
import java.util.Set;

/**
 * Services associés à la gestion des investissements
 *
 * @author mbouhamyd
 */

@Service
public class InvestmentTitresService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvestmentTitresService.class);

    private final InvestissementClient investissementClient;

    @Autowired
    public InvestmentTitresService(InvestissementClient investissementClient) {
        this.investissementClient = investissementClient;
    }

    Observable<Set<InvestissementTitre>> findAllTitresAssuranceVieNOBCByNumero(String numeroassurance, String codeRacine,
                                                                               AuthenticatedUser user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(numeroassurance);
        String login = Objects.requireNonNull(user.getLogin());
        LOGGER.info("Recherche les titres de l'assurance {}  ", numeroassurance);
        return investissementClient.findLifeAssuranceNOBCTitresById(numeroassurance, login, codeRacine, user.getToken())
                .doOnNext(titres -> LOGGER.info("Récupération les titres de l'assurance {}", numeroassurance))
                .doOnError(err -> {
                    String msg = String.format("Impossible de récupérer les titres de l'assurance vie(NOBC) %s du client %s de la racine %s",
                            numeroassurance, login, codeRacine);
                    NOBCException.throwEfsError(msg, err);
                });
    }

    Observable<InvestissementTitre> findTitrePortefeuillesById(String numeroPortefeuilles,
                                                               String numeroTitre,
                                                               String codeRacine,
                                                               AuthenticatedUser user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(numeroPortefeuilles);
        String login = Objects.requireNonNull(user.getLogin());
        LOGGER.info("Recherche le titre {} du portefeuilles {}  ", numeroTitre, numeroPortefeuilles);
        return investissementClient.findPortefeuillesTitresById(numeroPortefeuilles, login, codeRacine, user.getToken())
                .doOnNext(titres -> LOGGER.info("Récupération le titre {} du portefeuilles {}", numeroTitre, numeroPortefeuilles))
                .doOnError(err -> {
                    String msg = String.format("Impossible de récupérer le titre %s du portefeuilles %s du client %s de la racine %s",
                            numeroTitre, numeroPortefeuilles, login, codeRacine);
                    NOBCException.throwEfsError(msg, err);
                })
                .flatMap(Observable::from)
                .first(p -> p.getCode().equals(numeroTitre));
    }

    Observable<InvestissementTitre> findTitreAssuranceVieById(String numeroAssurance,
                                                              String numeroTitre,
                                                              String codeRacine,
                                                              AuthenticatedUser user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(numeroAssurance);
        String login = Objects.requireNonNull(user.getLogin());
        LOGGER.info("Recherche le titre {} du l'assurance externe {}  ", numeroTitre, numeroAssurance);
        return investissementClient.findLifeAssuranceTitresById(numeroAssurance, login, codeRacine, user.getToken())
                .doOnNext(titres -> LOGGER.info("Récupération le titre {} du l'assurance externe {}", numeroTitre, numeroAssurance))
                .doOnError(err -> {
                    String msg = String.format("Impossible de récupérer le titre %s de l'assurance vie %s du client %s de la racine %s",
                            numeroTitre, numeroAssurance, login, codeRacine);
                    NOBCException.throwEfsError(msg, err);
                })
                .flatMap(Observable::from)
                .first(p -> p.getCode().equals(numeroTitre));
    }

    Observable<InvestissementTitre> findTitreAssuranceVieNOBCById(String numAssu,
                                                                  String numTitre,
                                                                  String codeRacine,
                                                                  AuthenticatedUser user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(numTitre);
        Objects.requireNonNull(numAssu);
        String login = Objects.requireNonNull(user.getLogin());
        LOGGER.info("Recherche le titre {} du l'assurance interne {}", numTitre, numAssu);
        return investissementClient.findLifeAssuranceNOBCTitresById(numAssu, login, codeRacine, user.getToken())
                .doOnNext(titres -> LOGGER.info("Récupération le titre {} du l'assurance interne {}", numTitre, numAssu))
                .doOnError(err -> {
                    String msg = String.format("Impossible de récupérer le titre %s de l'assurance vie(NOBC) %s du client %s de la racine %s",
                            numTitre, numAssu, login, codeRacine);
                    NOBCException.throwEfsError(msg, err);
                })
                .flatMap(Observable::from)
                .first(p -> p.getCode().equals(numTitre));
    }

    Observable<Set<InvestissementTitre>> findAllTitresPortefeuillesByNumero(String numPorteF, String codeRacine, AuthenticatedUser user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(numPorteF);
        String login = Objects.requireNonNull(user.getLogin());
        LOGGER.info("Recherche les titres du portefeuilles {}", numPorteF);
        return investissementClient.findPortefeuillesTitresById(numPorteF, login, codeRacine, user.getToken())
                .doOnNext(titres -> LOGGER.info("Récupération les titres du portefeuilles {}", numPorteF))
                .doOnError(err -> {
                    String msg = String.format("Impossible de récupérer les titres du portefeuilles %s du client %s de la racine %s", numPorteF, login, codeRacine);
                    NOBCException.throwEfsError(msg, err);
                });
    }

    Observable<Set<InvestissementTitre>> findAllTitresAssuranceVieByNumero(String numAssurance,
                                                                           String codeRacine,
                                                                           AuthenticatedUser user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(numAssurance);
        String login = Objects.requireNonNull(user.getLogin());
        LOGGER.info("Recherche les titres du la une assurance {}", numAssurance);
        return investissementClient.findLifeAssuranceTitresById(numAssurance, login, codeRacine, user.getToken())
                .doOnNext(titres -> LOGGER.info("Récupération les titres du l'assurance {}", numAssurance))
                .doOnError(err -> {
                    String msg = String.format("Impossible de récupérer les titres de l'assurance vie %s du client %s de la racine %s",
                            numAssurance, login, codeRacine);
                    NOBCException.throwEfsError(msg, err);
                });
    }
}
