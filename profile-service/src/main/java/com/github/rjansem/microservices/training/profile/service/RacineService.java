package com.github.rjansem.microservices.training.profile.service;

import com.github.rjansem.microservices.training.exception.APIException;
import com.github.rjansem.microservices.training.profile.client.RacineClient;
import com.github.rjansem.microservices.training.profile.domain.efs.Racine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Services associés à la gestion des {@link Racine}
 *
 * @author rjansem
 * @author rjansem
 */
@Service
public class RacineService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RacineService.class);

    private final RacineClient racineClient;

    private static final String PROFIL_RACINE_PARTICULIER = "P";

    @Autowired
    public RacineService(RacineClient racineClient) {
        this.racineClient = racineClient;
    }

    Observable<Set<String>> findCodesRacinesByLogin(String userId) {
        return findByLogin(userId)
                .map(racines -> racines.stream().map(Racine::getCode).collect(Collectors.toSet()));
    }

    Observable<Set<Racine>> findByLogin(String userId) {
        Objects.requireNonNull(userId);
        String login = Objects.requireNonNull(userId);
        LOGGER.info("Recherche des racines pour l'utilisateur {}", login);
        Observable<Set<Racine>> racinesFromEfs = racinesFromEfs(userId, login);
        return racinesFromEfs;
    }

    public Observable<Racine> findRacineById(String userId, String id) {
        Objects.requireNonNull(userId);
        String login = Objects.requireNonNull(userId);
        LOGGER.info("Recherche la racine {} de l'utilisateur {}", id, login);
        Observable<Set<Racine>> racinesFromEfs = racinesFromEfs(userId, login);
        return racinesFromEfs.flatMap(Observable::from)
                .first(p -> p.getCode().equals(id));
    }

    private Observable<Set<Racine>> racinesFromEfs(String userId, String login) {
        return racineClient.findByLogin(login)
                .doOnNext(racines -> LOGGER.info("Récupération de {} racine(s) pour l'utilisateur {}", racines.size(), login))
                .doOnError(err -> {
                    String msg = String.format("Impossible de récupérer les racines de l'utilisateur %s", login);
                    APIException.throwEfsError(msg, err);
                });
    }

}
