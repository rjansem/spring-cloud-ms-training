package com.github.rjansem.microservices.training.profile.api;

import com.github.rjansem.microservices.training.profile.domain.apigateway.Token;
import com.github.rjansem.microservices.training.profile.service.TokenService;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.profile.domain.apigateway.Token;
import com.github.rjansem.microservices.training.profile.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rx.Single;

/**
 * Ressource permettant de récupérer les informations nécessaires à la génération d'un token
 *
 * @author jntakpe
 */
@RestController
@RequestMapping(ApiConstants.TOKEN)
public class TokenResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenResource.class);

    private final TokenService tokenService;

    @Autowired
    public TokenResource(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Single<Token> findTokenInfos(@AuthenticationPrincipal AuthenticatedUser user) {
        return tokenService.findTokenInfosByLogin(user)
                .doOnNext(a -> LOGGER.info("[ANALYTICS] [{}] [Profile] [TokenInfo]", user.getLogin()))
                .toSingle();
    }

}