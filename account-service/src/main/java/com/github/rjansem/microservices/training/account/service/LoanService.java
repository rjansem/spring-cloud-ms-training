package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.client.CreditClient;
import com.github.rjansem.microservices.training.account.domain.cache.CreditCacheKey;
import com.github.rjansem.microservices.training.account.domain.efs.credit.Credit;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.mapper.account.loan.CreditToAccountMapper;
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
 * Services associés à la gestion des credits
 *
 * @author aazzerrifi
 */
@Service
public class LoanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanService.class);

    private static final String LOAN_CACHE = "loanCache";

    private final CreditClient creditClient;

    private final Cache loanCache;

    @Autowired
    public LoanService(CreditClient creditClient, RedisCacheManager redisCacheManager) {
        this.creditClient = creditClient;
        this.loanCache = redisCacheManager.getCache(LOAN_CACHE);
    }

    Observable<List<Account>> findAllLoansByLoginAndRacine(AuthenticatedUser user, String codeRacine) {
        String login = user.getLogin();
        LOGGER.info("Recherche des credits pour du client {} avec la racine {}", login, codeRacine);
        CreditToAccountMapper mapper = new CreditToAccountMapper();
        Observable<Set<Credit>> creditsFromCache = creditsFromCache(login, codeRacine);
        Observable<Set<Credit>> creditsFromEfs = creditsFromEfs(user, codeRacine, login);
        return Observable.concat(creditsFromCache, creditsFromEfs)
                .first()
                .flatMap(Observable::from)
                .map(mapper::map)
                .toList();
    }

    private Observable<Set<Credit>> creditsFromEfs(AuthenticatedUser user, String codeRacine, String login) {
        return creditClient.findCreditByLoginAndRacine(login, codeRacine, user.getToken())
                .doOnNext(accs -> LOGGER.info("Récupération de {} credit(s) pour le client {} et la racine {}", accs.size(), login,
                        codeRacine))
                .doOnNext(accs -> cacheCredits(login, codeRacine, accs))
                .doOnError(err -> {
                    String msg = String.format("Impossible de récupérer les crédits du client %s de la racine %s", login, codeRacine);
                    NOBCException.throwEfsError(msg, err);
                });
    }

    private Observable<Set<Credit>> creditsFromCache(String login, String codeRacine) {
        return Optional.ofNullable(loanCache.get(new CreditCacheKey(login, codeRacine).key(), Set.class))
                .map(c -> createCreditsObs(c, login, codeRacine))
                .orElse(Observable.empty());
    }

    private Observable<Set<Credit>> createCreditsObs(Set credits, String login, String codeRacine) {
        LOGGER.info("Crédits de l'utilisateur {} avec la racine {} récupérés depuis le cache", login, codeRacine);
        return Observable.just((Set<Credit>) credits);
    }

    private void cacheCredits(String login, String codeRacine, Set<Credit> credits) {
        LOGGER.info("Ajout des crédits de l'utilisateur {} avec la racine {} au cache", login, codeRacine);
        loanCache.put(new CreditCacheKey(login, codeRacine).key(), credits);
    }
}

