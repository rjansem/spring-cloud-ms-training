package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.client.ClientConstants;
import com.github.rjansem.microservices.training.account.client.DeviseClient;
import com.github.rjansem.microservices.training.account.domain.efs.devise.Devise;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.exception.NOBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.github.rjansem.microservices.training.account.client.ClientConstants.EURO;

/**
 * Service pour la gestion de la devise
 *
 * @author mbouhamyd
 */
@Service
public class DeviseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviseService.class);

    private final DeviseClient deviseClient;

    private static final String DEVISES_CACHE_NAME = "devisesCache";

    private final Cache devisesCache;

    @Autowired
    public DeviseService(DeviseClient deviseClient, RedisCacheManager redisCacheManager) {
        this.deviseClient = deviseClient;
        devisesCache = redisCacheManager.getCache(this.DEVISES_CACHE_NAME);
    }

    Observable<Account> addDeviseToAccount(Account account, AuthenticatedUser user) {
        if (account.getCurrency() == null) {
            return Observable.just(account);
        }
        return Observable.just(account)
                .filter(a -> a.getCurrency() != null)
                .flatMap(a -> codeDeviseToCurrency(a.getCurrency(), user).map(a::setCurrency))
                .map(a -> a.setBookedBalanceCurrency(a.getCurrency()))
                .map(this::addTotalCurrentyToAccount)
                .flatMap(a -> findCounterValue(a, user));
    }

    Observable<String> codeDeviseToCurrency(String idDevise, AuthenticatedUser user) {
        Objects.requireNonNull(idDevise);
        LOGGER.info("Recherche de la devise {}", idDevise);

        Observable<String> codeDeviseFromCache = codeDeviseToCurrencyFromCache(idDevise, user);
        Observable<String> codeDeviseFromEfs = codeDeviseToCurrencyFromEfs(idDevise, user);
        return Observable.concat(codeDeviseFromCache, codeDeviseFromEfs).first();
    }

    private Observable<String> codeDeviseToCurrencyFromCache(String idDevise, AuthenticatedUser user) {
        return Optional.ofNullable(devisesCache.get(idDevise, Devise.class))
                .map(this::createObs)
                .orElse(Observable.empty());
    }

    private Observable<String> codeDeviseToCurrencyFromEfs(String idDevise, AuthenticatedUser user) {
        return deviseClient.findDeviseById(idDevise, user.getToken())
                .doOnNext(devise -> LOGGER.info("Récupération du détail de la devise {} {}", idDevise, devise))
                .doOnError(err -> {
                    String msg = String.format("Erreur lors de la récupération du detail de la devise %s", idDevise);
                    NOBCException.throwEfsError(msg, err);
                })
                .map(devise -> {
                    devisesCache.put(idDevise, devise);
                    return devise.getCode();
                });
    }

    private Observable<String> createObs(Devise devise) {
        LOGGER.info("Récupération du détail de la devise {} depuis le cache", devise.getId());
        return Observable.just(devise.getCode());
    }

    private Account addTotalCurrentyToAccount(Account a) {
        if (a.getCreditcardsTotalBalance() != null) {
            a.setCreditcardsTotalCurrency(a.getCurrency());
        }
        return a;
    }

    private Observable<Account> findCounterValue(Account account, AuthenticatedUser user) {
        if (account.getBookedBalance() == null || account.getBookedBalanceCurrency().equals(ClientConstants.EURO)) {
            return Observable.just(account);
        }
        return Observable.just(account)
                .flatMap(a -> findRate(a.getCurrency(), user).map(a::setCountervalueRate))
                .map(a -> {
                    if (a.getCountervalueRate() != null && a.getCountervalueRate() != BigDecimal.ZERO) {
                        a.setCountervalueBalance(a.getBookedBalance().divide(a.getCountervalueRate(), 2, BigDecimal.ROUND_HALF_UP));
                    }
                    return a;
                })
                .map(a -> a.setCountervalueDate(LocalDateTime.now()).setCountervalueCurrency(ClientConstants.EURO));
    }

    private Observable<BigDecimal> findRate(String idDevise, AuthenticatedUser user) {
        Objects.requireNonNull(idDevise);
        LOGGER.info("Recherche la devise {}", idDevise);
        Devise deviseFromCache = devisesCache.get(idDevise, Devise.class);

        if(deviseFromCache != null) {
            LOGGER.info("Récupération du rate de la devise {} depuis le cache", idDevise);
            return Observable.just(new BigDecimal(deviseFromCache.getEuroCours()));
        }
        return deviseClient.findDeviseById(idDevise, user.getToken())
                .doOnNext(devise -> LOGGER.info("Récupération du rate de la devise {} {}", idDevise, devise))
                .doOnError(err -> {
                    String msg = String.format("Erreur lors de la récupération du rate de la devise %s", idDevise);
                    NOBCException.throwEfsError(msg, err);
                })
                .map(Devise::getEuroCours)
                .map(BigDecimal::new);
    }
}