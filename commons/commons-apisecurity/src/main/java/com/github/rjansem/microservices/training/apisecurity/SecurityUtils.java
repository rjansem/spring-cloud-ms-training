package com.github.rjansem.microservices.training.apisecurity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

/**
 * Classe contenant les méthodes utilitaires de sécurité
 *
 * @author jntakpe
 */
final class SecurityUtils {

    private SecurityUtils() {
    }

    private static AuthenticatedUser getCurrentUserOrThrow() {
        return getCurrentUser().orElseThrow(() -> new UsernameNotFoundException("Impossible de récupérer l'utilisateur courant"));
    }

    private static Optional<AuthenticatedUser> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof AuthenticatedUser) {
            AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();
            return Optional.of(authenticatedUser);
        }
        return Optional.empty();
    }

    public static boolean currentUserHasRacine(String racine) {
        List<String> racines = getCurrentUserOrThrow().getRacines();
        return StringUtils.isNotEmpty(racine) && !CollectionUtils.isEmpty(racines) && racines.contains(racine);
    }

}
