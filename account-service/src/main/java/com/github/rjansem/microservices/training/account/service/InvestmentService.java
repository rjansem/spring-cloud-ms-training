package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.client.ClientConstants;
import com.github.rjansem.microservices.training.account.client.CompteClient;
import com.github.rjansem.microservices.training.account.domain.efs.investissement.AssuranceVie;
import com.github.rjansem.microservices.training.account.domain.efs.investissement.PortefeuilleTitres;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.mapper.account.investissement.AssuranceVieToAccountMapper;
import com.github.rjansem.microservices.training.account.mapper.account.investissement.PortefeuillesTitresToAccountMapper;
import com.github.rjansem.microservices.training.exception.NOBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Services associés à la gestion des investissements
 *
 * @author aazzerrifi
 * @author rjansem
 */
@Service
public class InvestmentService {

    private static final String ASSURANCES_VIE_CACHE = "asvCache";

    private static final String PORTFOLIOS_CACHE = "portfoliosCache";

    private static final Logger LOGGER = LoggerFactory.getLogger(InvestmentService.class);

    private final CompteClient compteClient;

    @Autowired
    public InvestmentService(CompteClient compteClient) {
        this.compteClient = compteClient;
    }

    Observable<Set<Account>> findPortfolioByClientLogin(String userId, String codeRacine) {
        String login = userId;
        LOGGER.info("Recherche des portefeuilles du client {} de la racine {}", login, codeRacine);
        PortefeuillesTitresToAccountMapper mapper = new PortefeuillesTitresToAccountMapper();
        Observable<Set<PortefeuilleTitres>> portFromEfs = portfoliosFromEfs(userId, codeRacine, login);
        return portFromEfs.flatMap(Observable::from)
                .map(mapper::map)
                .toList()
                .map(HashSet::new);
    }

    Observable<Set<Account>> findLifeInsuranceByLogin(String userId, String codeRacine) {
        String login = userId;
        LOGGER.info("Recherche des assurances vie pour du client {} avec la racine {}", login, codeRacine);
        AssuranceVieToAccountMapper mapper = new AssuranceVieToAccountMapper();
        Observable<Set<AssuranceVie>> asvFromEfs = asvFromEfs(userId, codeRacine, login);
        return asvFromEfs.flatMap(Observable::from)
                .map(mapper::map)
                .toList()
                .map(HashSet::new);
    }

    Observable<List<Account>> findAllInvestmentsByLoginAndRacine(String userId, String codeRacine) {
        String login = userId;
        LOGGER.info("Recherche de tous les comptes d'investissement du client {} avec la racine {}", login, codeRacine);
        Observable<Set<Account>> portfolioAccounts = findPortfolioByClientLogin(userId, codeRacine);
        Observable<Set<Account>> lifeInsuranceAccounts = findLifeInsuranceByLogin(userId, codeRacine);
        return Observable.merge(portfolioAccounts, lifeInsuranceAccounts)
                .toList()
                .map(l -> l.stream().flatMap(Set::stream).collect(Collectors.toList()))
                .doOnNext(accs -> LOGGER.info("Récupération de {} compte(s) d'investissement pour le client {} et la racine {}",
                        accs.size(), login, codeRacine))
                .doOnError(e -> {
                    String msg = String.format("Erreur lors de la récupération des comptes d'investissement du client %s de la racine %s",
                            login,
                            codeRacine);
                    NOBCException.throwEfsError(msg, e);
                });
    }

    private Observable<Set<AssuranceVie>> asvFromEfs(String userId, String codeRacine, String login) {
        return compteClient.findAssuranceVieByLoginAndRacine(login, codeRacine)
                .doOnNext(accs -> LOGGER.info("Récupération de {} assurance(s) vie pour le client {} et la racine {}", accs.size(), login,
                        codeRacine))
                .doOnError(e -> {
                    String msg = String.format("Erreur lors de la récupération des assurances vie du client %s de la racine %s", login,
                            codeRacine);
                    NOBCException.throwEfsError(msg, e);
                });
    }

    private Observable<Set<PortefeuilleTitres>> portfoliosFromEfs(String userId, String codeRacine, String login) {
        return compteClient.findPortefeuilleTitresByLoginAndRacine(login, codeRacine, ClientConstants.PP)
                .doOnNext(accs -> LOGGER.info("Récupération de {} portefeuille(s) du client {} de la racine {}", accs.size(),
                        login, codeRacine))
                .doOnError(e -> {
                    String msg = String.format("Erreur lors de la récupération des portefeuilles du client %s de la racine %s",
                            login, codeRacine);
                    NOBCException.throwEfsError(msg, e);
                });
    }


}

