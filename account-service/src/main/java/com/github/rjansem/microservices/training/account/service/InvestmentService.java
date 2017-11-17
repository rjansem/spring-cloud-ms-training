package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.client.ClientConstants;
import com.github.rjansem.microservices.training.account.client.CompteClient;
import com.github.rjansem.microservices.training.account.domain.cache.CompteCacheKey;
import com.github.rjansem.microservices.training.account.domain.efs.investissement.AssuranceVie;
import com.github.rjansem.microservices.training.account.domain.efs.investissement.PortefeuilleTitres;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.mapper.account.investissement.AssuranceVieToAccountMapper;
import com.github.rjansem.microservices.training.account.mapper.account.investissement.PortefeuillesTitresToAccountMapper;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.exception.NOBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.rjansem.microservices.training.account.client.ClientConstants.PP;

/**
 * Services associés à la gestion des investissements
 *
 * @author aazzerrifi
 * @author jntakpe
 */
@Service
public class InvestmentService {

    private static final String ASSURANCES_VIE_CACHE = "asvCache";

    private static final String PORTFOLIOS_CACHE = "portfoliosCache";

    private static final Logger LOGGER = LoggerFactory.getLogger(InvestmentService.class);

    private final CompteClient compteClient;

    private final RedisCacheManager redisCacheManager;

    @Autowired
    public InvestmentService(CompteClient compteClient, RedisCacheManager redisCacheManager) {
        this.compteClient = compteClient;
        this.redisCacheManager = redisCacheManager;
    }

    Observable<Set<Account>> findPortfolioByClientLogin(AuthenticatedUser user, String codeRacine) {
        String login = user.getLogin();
        LOGGER.info("Recherche des portefeuilles du client {} de la racine {}", login, codeRacine);
        PortefeuillesTitresToAccountMapper mapper = new PortefeuillesTitresToAccountMapper();
        Observable<Set<PortefeuilleTitres>> portFromEfs = portfoliosFromEfs(user, codeRacine, login);
        Observable<Set<PortefeuilleTitres>> portFromCache = portfoliosFromCache(login, codeRacine, ClientConstants.PP);
        return Observable.concat(portFromCache, portFromEfs)
                .first()
                .flatMap(Observable::from)
                .map(mapper::map)
                .toList()
                .map(HashSet::new);
    }

    Observable<Set<Account>> findLifeInsuranceByLogin(AuthenticatedUser user, String codeRacine) {
        String login = user.getLogin();
        LOGGER.info("Recherche des assurances vie pour du client {} avec la racine {}", login, codeRacine);
        AssuranceVieToAccountMapper mapper = new AssuranceVieToAccountMapper();
        Observable<Set<AssuranceVie>> asvFromEfs = asvFromEfs(user, codeRacine, login);
        Observable<Set<AssuranceVie>> asvFromCache = asvFromCache(login, codeRacine, ClientConstants.PP);
        return Observable.concat(asvFromCache, asvFromEfs)
                .first()
                .flatMap(Observable::from)
                .map(mapper::map)
                .toList()
                .map(HashSet::new);
    }

    Observable<List<Account>> findAllInvestmentsByLoginAndRacine(AuthenticatedUser user, String codeRacine) {
        String login = user.getLogin();
        LOGGER.info("Recherche de tous les comptes d'investissement du client {} avec la racine {}", login, codeRacine);
        Observable<Set<Account>> portfolioAccounts = findPortfolioByClientLogin(user, codeRacine);
        Observable<Set<Account>> lifeInsuranceAccounts = findLifeInsuranceByLogin(user, codeRacine);
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

    private Observable<Set<AssuranceVie>> asvFromEfs(AuthenticatedUser user, String codeRacine, String login) {
        return compteClient.findAssuranceVieByLoginAndRacine(login, codeRacine, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération de {} assurance(s) vie pour le client {} et la racine {}", accs.size(), login,
                        codeRacine))
                .doOnNext(accs -> cacheAsv(login, codeRacine, ClientConstants.PP, accs))
                .doOnError(e -> {
                    String msg = String.format("Erreur lors de la récupération des assurances vie du client %s de la racine %s", login,
                            codeRacine);
                    NOBCException.throwEfsError(msg, e);
                });
    }

    private Observable<Set<PortefeuilleTitres>> portfoliosFromEfs(AuthenticatedUser user, String codeRacine, String login) {
        return compteClient.findPortefeuilleTitresByLoginAndRacine(login, codeRacine, ClientConstants.PP, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération de {} portefeuille(s) du client {} de la racine {}", accs.size(),
                        login, codeRacine))
                .doOnNext(accs -> cachePortfolios(login, codeRacine, ClientConstants.PP, accs))
                .doOnError(e -> {
                    String msg = String.format("Erreur lors de la récupération des portefeuilles du client %s de la racine %s",
                            login, codeRacine);
                    NOBCException.throwEfsError(msg, e);
                });
    }

    private Observable<Set<AssuranceVie>> asvFromCache(String login, String codeRacine, String codeProfil) {
        Cache asvCache = redisCacheManager.getCache(ASSURANCES_VIE_CACHE);
        return Optional.ofNullable(asvCache.get(new CompteCacheKey(login, codeRacine, codeProfil).key(), Set.class))
                .map(c -> createASVObs(c, login, codeRacine))
                .orElse(Observable.empty());
    }

    private Observable<Set<AssuranceVie>> createASVObs(Set asvs, String login, String codeRacine) {
        LOGGER.info("Assurances vie de l'utilisateur {} avec la racine {} récupérées depuis le cache", login, codeRacine);
        return Observable.just((Set<AssuranceVie>) asvs);
    }

    private void cacheAsv(String login, String codeRacine, String codeProfil, Set<AssuranceVie> asvs) {
        LOGGER.info("Ajout des assurances vies de l'utilisateur {} avec la racine {} au cache", login, codeRacine);
        Cache asvCache = redisCacheManager.getCache(ASSURANCES_VIE_CACHE);
        asvCache.put(new CompteCacheKey(login, codeRacine, codeProfil).key(), asvs);
    }

    private Observable<Set<PortefeuilleTitres>> portfoliosFromCache(String login, String codeRacine, String codeProfil) {
        Cache portCache = redisCacheManager.getCache(PORTFOLIOS_CACHE);
        return Optional.ofNullable(portCache.get(new CompteCacheKey(login, codeRacine, codeProfil).key(), Set.class))
                .map(c -> createPortObs(c, login, codeRacine))
                .orElse(Observable.empty());
    }

    private Observable<Set<PortefeuilleTitres>> createPortObs(Set portefeuilles, String login, String codeRacine) {
        LOGGER.info("Portefeuilles titres de l'utilisateur {} avec la racine {} récupérés depuis le cache", login, codeRacine);
        return Observable.just((Set<PortefeuilleTitres>) portefeuilles);
    }

    private void cachePortfolios(String login, String codeRacine, String codeProfil, Set<PortefeuilleTitres> portefeuillesTitres) {
        LOGGER.info("Ajout des portefeuilles titres de l'utilisateur {} avec la racine {} au cache", login, codeRacine);
        Cache portCache = redisCacheManager.getCache(PORTFOLIOS_CACHE);
        portCache.put(new CompteCacheKey(login, codeRacine, codeProfil).key(), portefeuillesTitres);
    }
}

