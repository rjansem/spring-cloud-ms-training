package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.client.ClientConstants;
import com.github.rjansem.microservices.training.account.client.CompteClient;
import com.github.rjansem.microservices.training.account.domain.cache.CompteCacheKey;
import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteCourant;
import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteEpargne;
import com.github.rjansem.microservices.training.account.domain.efs.compte.DepotATerme;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.mapper.account.chekingAndSaving.CompteCourantToAccountMapper;
import com.github.rjansem.microservices.training.account.mapper.account.chekingAndSaving.CompteEpargneToAccountMapper;
import com.github.rjansem.microservices.training.account.mapper.account.chekingAndSaving.DepotsATermeToAccountMapper;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.exception.NOBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.*;

import static com.github.rjansem.microservices.training.account.client.ClientConstants.PP;

/**
 * Services associés à la gestion des comptes courant et épargnes
 *
 * @author aazzerrifi
 * @author jntakpe
 */
@Service
public class ChekingAndSavingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChekingAndSavingService.class);

    private static final String COMPTES_COURANTS_CACHE = "comptesCourantsCache";

    private static final String COMPTES_EPARGNES_CACHE = "comptesEpargnesCache";

    private static final String DEPOT_A_TERME_CACHE = "depotsATermeCache";

    private final CompteClient compteClient;

    private final RedisCacheManager redisCacheManager;

    @Autowired
    public ChekingAndSavingService(CompteClient compteClient, RedisCacheManager redisCacheManager) {
        this.compteClient = compteClient;
        this.redisCacheManager = redisCacheManager;
    }


    Observable<List<Account>> findAllAccountsByRacine(AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        return Observable.merge(
                findCheckingsAccountsByClientLogin(user, codeRacine),
                findSavingsAccountsByLogin(user, codeRacine),
                findDepotsATermeAccountsByClientLogin(user, codeRacine))
                .toList();
    }

    Observable<Set<Account>> findCheckingsAndSavingsWithoutDATByLoginAndRacine(AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        Observable<Account> csAccsObs = findCheckingsAccountsByClientLogin(user, codeRacine);
        Observable<Account> savingsAccsObs = findSavingsAccountsByLogin(user, codeRacine);
        return Observable.merge(csAccsObs, savingsAccsObs).toList().map(HashSet::new);
    }

    Observable<Account> findSavingsAccountsByLogin(AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche des comptes épargne pour du client {} avec la racine {}", login, codeRacine);
        CompteEpargneToAccountMapper mapper = new CompteEpargneToAccountMapper();
        Observable<Set<CompteEpargne>> ceFromCache = comptesEpargneFromCache(login, codeRacine, ClientConstants.PP);
        Observable<Set<CompteEpargne>> ceFromEfs = comptesEpargneFromEfs(user, codeRacine, login);
        return Observable.concat(ceFromCache, ceFromEfs)
                .first()
                .flatMap(Observable::from)
                .map(mapper::map);
    }

    Observable<Account> findCheckingsAccountsByClientLogin(AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche des comptes courant pour du client {} avec la racine {}", login, codeRacine);
        CompteCourantToAccountMapper mapper = new CompteCourantToAccountMapper();
        Observable<Set<CompteCourant>> ccFromCache = comptesCourantsFromCache(login, codeRacine, ClientConstants.PP);
        Observable<Set<CompteCourant>> ccFromEfs = comptesCourantsFromEfs(user, codeRacine, login);
        return Observable.concat(ccFromCache, ccFromEfs)
                .first()
                .flatMap(Observable::from)
                .map(mapper::map);
    }

    Observable<Account> findDepotsATermeAccountsByClientLogin(AuthenticatedUser user, String codeRacine) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Recherche des depots à terme pour le client {} avec la racine {}", login, codeRacine);
        DepotsATermeToAccountMapper mapper = new DepotsATermeToAccountMapper();
        Observable<Set<DepotATerme>> datFromCache = datFromCache(login, codeRacine, ClientConstants.PP);
        Observable<Set<DepotATerme>> datFromEfs = depotsATermeFromEfs(user, codeRacine, login);
        return Observable.concat(datFromCache, datFromEfs)
                .first()
                .flatMap(Observable::from)
                .map(mapper::map);
    }

    private Observable<Set<CompteCourant>> comptesCourantsFromEfs(AuthenticatedUser user, String codeRacine, String login) {
        return compteClient.findCompteCourantByLoginAndRacine(login, codeRacine, ClientConstants.PP, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération de {} compte(s) courant pour le client {} avec la racine {}", accs.size(),
                        login, codeRacine))
                .doOnNext(accs -> cacheComptesCourants(login, codeRacine, ClientConstants.PP, accs))
                .doOnError(e -> {
                    String msg = String.format("Impossible de récupérer les comptes courant de %s avec la racine %s", login, codeRacine);
                    NOBCException.throwEfsError(msg, e);
                });
    }

    private Observable<Set<DepotATerme>> depotsATermeFromEfs(AuthenticatedUser user, String codeRacine, String login) {
        return compteClient.findDepotATermeByLoginAndRacine(login, codeRacine, ClientConstants.PP, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération de {} depot(s) à terme pour le client {} avec la racine {}", accs.size(),
                        login, codeRacine))
                .doOnNext(accs -> cacheDepotsATerme(login, codeRacine, ClientConstants.PP, accs))
                .doOnError(err -> {
                    String msg = String.format("Impossible de récupérer les depots à terme de %s avec la racine %s", login, codeRacine);
                    NOBCException.throwEfsError(msg, err);
                });
    }

    private Observable<Set<CompteEpargne>> comptesEpargneFromEfs(AuthenticatedUser user, String codeRacine, String login) {
        return compteClient.findCompteEpargneByLoginAndRacine(login, codeRacine, ClientConstants.PP, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération de {} compte(s) épargne pour le client {} avec la racine {}", accs.size(),
                        login, codeRacine))
                .doOnNext(accs -> cacheComptesEpargnes(login, codeRacine, ClientConstants.PP, accs))
                .doOnError(err -> {
                    String msg = String.format("Impossible de récupérer les comptes épargne du client %s avec la racine %s", login,
                            codeRacine);
                    NOBCException.throwEfsError(msg, err);
                });
    }

    private Observable<Set<CompteCourant>> comptesCourantsFromCache(String login, String codeRacine, String codeProfil) {
        Cache ccCache = redisCacheManager.getCache(COMPTES_COURANTS_CACHE);
        return Optional.ofNullable(ccCache.get(new CompteCacheKey(login, codeRacine, codeProfil).key(), Set.class))
                .map(c -> createCCObs(c, login, codeRacine))
                .orElse(Observable.empty());
    }

    private Observable<Set<CompteCourant>> createCCObs(Set comptesCourant, String login, String codeRacine) {
        LOGGER.info("Comptes courant de l'utilisateur {} avec la racine {} récupérés depuis le cache", login, codeRacine);
        return Observable.just((Set<CompteCourant>) comptesCourant);
    }

    private void cacheComptesCourants(String login, String codeRacine, String codeProfil, Set<CompteCourant> compteCourants) {
        LOGGER.info("Ajout des comptes courants de l'utilisateur {} avec la racine {} au cache", login, codeRacine);
        Cache ccCache = redisCacheManager.getCache(COMPTES_COURANTS_CACHE);
        ccCache.put(new CompteCacheKey(login, codeRacine, codeProfil).key(), compteCourants);
    }

    private Observable<Set<CompteEpargne>> comptesEpargneFromCache(String login, String codeRacine, String codeProfil) {
        Cache ceCache = redisCacheManager.getCache(COMPTES_EPARGNES_CACHE);
        return Optional.ofNullable(ceCache.get(new CompteCacheKey(login, codeRacine, codeProfil).key(), Set.class))
                .map(c -> createCEObs(c, login, codeRacine))
                .orElse(Observable.empty());
    }

    private Observable<Set<CompteEpargne>> createCEObs(Set comptesEpargnes, String login, String codeRacine) {
        LOGGER.info("Comptes épargne de l'utilisateur {} avec la racine {} récupérés depuis le cache", login, codeRacine);
        return Observable.just((Set<CompteEpargne>) comptesEpargnes);
    }

    private void cacheComptesEpargnes(String login, String codeRacine, String codeProfil, Set<CompteEpargne> compteEpargnes) {
        LOGGER.info("Ajout des comptes épargne de l'utilisateur {} avec la racine {} au cache", login, codeRacine);
        Cache ceCache = redisCacheManager.getCache(COMPTES_EPARGNES_CACHE);
        ceCache.put(new CompteCacheKey(login, codeRacine, codeProfil).key(), compteEpargnes);
    }

    private Observable<Set<DepotATerme>> datFromCache(String login, String codeRacine, String codeProfil) {
        Cache datCache = redisCacheManager.getCache(DEPOT_A_TERME_CACHE);
        return Optional.ofNullable(datCache.get(new CompteCacheKey(login, codeRacine, codeProfil).key(), Set.class))
                .map(c -> createDATObs(c, login, codeRacine))
                .orElse(Observable.empty());
    }

    private Observable<Set<DepotATerme>> createDATObs(Set dats, String login, String codeRacine) {
        LOGGER.info("Dépots à terme de l'utilisateur {} avec la racine {} récupérés depuis le cache", login, codeRacine);
        return Observable.just((Set<DepotATerme>) dats);
    }

    private void cacheDepotsATerme(String login, String codeRacine, String codeProfil, Set<DepotATerme> dats) {
        LOGGER.info("Ajout des dépots à terme de l'utilisateur {} avec la racine {} au cache", login, codeRacine);
        Cache datCache = redisCacheManager.getCache(DEPOT_A_TERME_CACHE);
        datCache.put(new CompteCacheKey(login, codeRacine, codeProfil).key(), dats);
    }
}

