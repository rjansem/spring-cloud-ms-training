package com.github.rjansem.microservices.training.commons.testing;

import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.apisecurity.Authority;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Permet de créer un context de sécurité pour les tests avec l'annotation {@link WithMockAuthenticatedUser}
 *
 * @author jntakpe
 */
public class WithMockAuthenticatedUserContextFactory implements WithSecurityContextFactory<WithMockAuthenticatedUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockAuthenticatedUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        AuthenticatedUser user = userFromAnnotation(annotation);
        Collection<? extends GrantedAuthority> authorities = authoritiesFromAnnotation(annotation);
        Authentication auth = new UsernamePasswordAuthenticationToken(user, "N/A", authorities);
        context.setAuthentication(auth);
        return context;
    }

    private AuthenticatedUser userFromAnnotation(WithMockAuthenticatedUser annotation) {
        List<String> racines = Arrays.asList(annotation.racines());
        List<Authority> authorities = Arrays.asList(annotation.authorities());
        String login = StringUtils.isNotEmpty(annotation.value()) ? annotation.value() : annotation.login();
        return new AuthenticatedUser("", login, racines, authorities);
    }

    private Collection<? extends GrantedAuthority> authoritiesFromAnnotation(WithMockAuthenticatedUser annotation) {
        return Arrays.stream(annotation.authorities()).map(a -> new SimpleGrantedAuthority(a.name())).collect(Collectors.toList());
    }
}
