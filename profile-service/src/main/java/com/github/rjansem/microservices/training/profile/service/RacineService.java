package com.github.rjansem.microservices.training.profile.service;

import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.commons.domain.utils.FindProfilByType;
import com.github.rjansem.microservices.training.exception.NOBCException;
import com.github.rjansem.microservices.training.profile.client.RacineClient;
import com.github.rjansem.microservices.training.profile.domain.efs.Abonne;
import com.github.rjansem.microservices.training.profile.domain.efs.Racine;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Services associés à la gestion des {@link Racine}
 *
 * @author jntakpe
 * @author aazzerrifi
 */
@Service
public class RacineService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RacineService.class);

    private static final String RACINES_CACHE_NAME = "racinesCache";

    private final RacineClient racineClient;

    private final Cache racinesCache;

    private static final String PROFIL_RACINE_PARTICULIER = "P";

    @Autowired
    public RacineService(RacineClient racineClient, RedisCacheManager redisCacheManager) {
        this.racineClient = racineClient;
        this.racinesCache = redisCacheManager.getCache(RACINES_CACHE_NAME);
    }

    Observable<Set<String>> findCodesRacinesByLogin(AuthenticatedUser user) {
        return findByLogin(user)
                .map(racines -> racines.stream().map(Racine::getCode).collect(Collectors.toSet()));
    }

    Observable<Set<Racine>> findByLogin(AuthenticatedUser user) {
        Objects.requireNonNull(user);
        String login = Objects.requireNonNull(user.getLogin());
        LOGGER.info("Recherche des racines pour l'utilisateur {}", login);
        Observable<Set<Racine>> racinesFromCache = racinesFromCache(login);
        Observable<Set<Racine>> racinesFromEfs = racinesFromEfs(user, login);
        return Observable.concat(racinesFromCache, racinesFromEfs)
                .first();
    }

    public Observable<Racine>findRacineById(AuthenticatedUser user, String id) {
        Objects.requireNonNull(user);
        String login = Objects.requireNonNull(user.getLogin());
        LOGGER.info("Recherche la racine {} de l'utilisateur {}",id, login);
        Observable<Set<Racine>> racinesFromCache = racinesFromCache(login);
        Observable<Set<Racine>> racinesFromEfs = racinesFromEfs(user, login);
        return Observable.concat(racinesFromCache, racinesFromEfs)
                .flatMap(Observable::from)
                .first(p -> p.getCode().equals(id));
    }

    private Observable<Set<Racine>> racinesFromEfs(AuthenticatedUser user, String login) {
        return racineClient.findByLogin(login, user.getToken())
                .doOnNext(racines -> LOGGER.info("Récupération de {} racine(s) pour l'utilisateur {}", racines.size(), login))
                .map(racines -> filterRacinesByCodeProfil(racines))
                .doOnNext(racines -> cacheRacines(login, racines))
                .doOnError(err -> {
                    String msg = String.format("Impossible de récupérer les racines de l'utilisateur %s", login);
                    NOBCException.throwEfsError(msg, err);
                });
    }

    private Set<Racine> filterRacinesByCodeProfil(Set<Racine> racines) {
        return racines.stream()
                .filter(racine ->  {
                    if(racine.getProfil().equals(PROFIL_RACINE_PARTICULIER)){
                        return true;
                    }
                  if(StringUtils.isBlank(racine.getProfil())){
                        if(FindProfilByType.findProfilByType((racine.getType())).getProfil().equals(PROFIL_RACINE_PARTICULIER)) {
                            return true;
                        }else{
                            return false;
                    }
                  }
                   return false;
                })
                .collect(Collectors.toSet());
    }
    private Observable<Set<Racine>> racinesFromCache(String login) {
        return Optional.ofNullable(racinesCache.get(login, Set.class))
                .map(r -> createObs(r, login))
                .orElse(Observable.empty());
    }

    private Observable<Set<Racine>> createObs(Set racines, String login) {
        LOGGER.info("Racines de l'utilisateur {} récupérées depuis le cache", login);
        return Observable.just((Set<Racine>) racines);
    }

    private void cacheRacines(String login, Set<Racine> racines) {
        LOGGER.info("Ajout des racines de l'utilisateur {} au cache", login);
        racinesCache.put(login, racines);
    }

    public Observable<Abonne> abonneFromEfs(AuthenticatedUser user) {
        return racineClient.findAbonne(user.getLogin(), user.getToken())
                .doOnNext(abonne -> LOGGER.info("Récupération de l'abonne {}", user.getLogin()))
                .doOnError(err -> {
                    String msg = String.format("Impossible de récupérer l'abonne %s", user.getLogin());
                    NOBCException.throwEfsError(msg, err);
                });
    }

}
