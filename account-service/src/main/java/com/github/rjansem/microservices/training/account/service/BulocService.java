package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.client.CreditClient;
import com.github.rjansem.microservices.training.account.domain.cache.CreditCacheKey;
import com.github.rjansem.microservices.training.account.domain.efs.credit.EngagementSignature;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.mapper.account.buloc.EngagementSignatureToAccountMapper;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.exception.NOBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Services associés à la gestion des engagements par signature (en anglais Back Up Line Of Credit, BULOC)
 *
 * @author aazzerrifi
 */
@Service
public class BulocService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BulocService.class);

    private static final String BULOC_CACHE = "bulocCache";

    private final CreditClient creditClient;

    private final Cache bulocCache;

    @Autowired
    public BulocService(CreditClient creditClient, RedisCacheManager redisCacheManager) {
        this.creditClient = creditClient;
        this.bulocCache = redisCacheManager.getCache(BULOC_CACHE);
    }

    Observable<List<Account>> findAllBulocsByLogin(AuthenticatedUser user, String codeRacine) {
        String login = user.getLogin();
        LOGGER.info("Recherche des engagements par signature pour du client {} avec la racine {}", login, codeRacine);
        EngagementSignatureToAccountMapper mapper = new EngagementSignatureToAccountMapper();
        Observable<Set<EngagementSignature>> engSignFromEfs = engSignFromEfs(user, codeRacine, login);
        Observable<Set<EngagementSignature>> engSignFromCache = engSFromCache(login, codeRacine);
        return Observable.concat(engSignFromCache, engSignFromEfs)
                .first()
                .flatMap(Observable::from)
                .map(mapper::map)
                .toList();
    }

    private Observable<Set<EngagementSignature>> engSignFromEfs(AuthenticatedUser user, String codeRacine, String login) {
        return creditClient.findEngagementSignatureByLoginAndRacine(login, codeRacine, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération de {} engagement(s) par signature pour le client {} et la racine {}",
                        accs.size(), login, codeRacine))
                .doOnNext(accs -> cacheEngagementsSign(login, codeRacine, accs))
                .doOnError(err -> {
                    String msg = String.format("Erreur lors de la récupération des engagements par signature du client %s de la" +
                            " racine %s", login, codeRacine);
                    NOBCException.throwEfsError(msg, err);
                });
    }

    private Observable<Set<EngagementSignature>> engSFromCache(String login, String codeRacine) {
        return Optional.ofNullable(bulocCache.get(new CreditCacheKey(login, codeRacine).key(), Set.class))
                .map(c -> createEngagementSignObs(c, login, codeRacine))
                .orElse(Observable.empty());
    }

    private Observable<Set<EngagementSignature>> createEngagementSignObs(Set credits, String login, String codeRacine) {
        LOGGER.info("Engagements signature de l'utilisateur {} avec la racine {} récupérés depuis le cache", login, codeRacine);
        return Observable.just((Set<EngagementSignature>) credits);
    }

    private void cacheEngagementsSign(String login, String codeRacine, Set<EngagementSignature> engagementSignatures) {
        LOGGER.info("Ajout des engagements signature de l'utilisateur {} avec la racine {} au cache", login, codeRacine);
        bulocCache.put(new CreditCacheKey(login, codeRacine).key(), engagementSignatures);
    }
}

