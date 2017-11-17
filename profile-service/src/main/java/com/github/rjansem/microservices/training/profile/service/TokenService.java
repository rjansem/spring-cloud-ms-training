package com.github.rjansem.microservices.training.profile.service;

import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.apisecurity.Authority;
import com.github.rjansem.microservices.training.profile.domain.apigateway.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.Objects;
import java.util.Set;

/**
 * Services associés à la génération d'un token
 *
 * @author jntakpe
 */
@Service
public class TokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenService.class);

    private final RacineService racineService;

    private final RoleService roleService;

    @Autowired
    public TokenService(RacineService racineService, RoleService roleService) {
        this.racineService = racineService;
        this.roleService = roleService;
    }

    public Observable<Token> findTokenInfosByLogin(AuthenticatedUser user) {
        Objects.requireNonNull(user);
        String login = user.getLogin();
        LOGGER.info("Génération d'un token pour l'utilisateur {}", login);
        Observable<Set<String>> obsRacines = racineService.findCodesRacinesByLogin(user);
        Observable<Set<Authority>> obsAuths = roleService.findAuthoritiesByLogin(user);
        return Observable.zip(obsRacines, obsAuths, (racines, auths) -> new Token(login, racines, auths));
    }

}
