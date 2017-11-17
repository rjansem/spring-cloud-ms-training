package com.github.rjansem.microservices.training.apisecurity.utils;

import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;
import java.util.Collections;

/**
 * Permet de créer un context de sécurité pour les tests avec l'annotation {@link WithMockRacines}
 *
 * @author jntakpe
 */
public class WithMockRacinesContextFactory implements WithSecurityContextFactory<WithMockRacines> {

    @Override
    public SecurityContext createSecurityContext(WithMockRacines annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        AuthenticatedUser user = new AuthenticatedUser("", "any", Arrays.asList(annotation.value()), Collections.emptyList());
        Authentication auth = new UsernamePasswordAuthenticationToken(user, "N/A", Collections.emptyList());
        context.setAuthentication(auth);
        return context;
    }

}
