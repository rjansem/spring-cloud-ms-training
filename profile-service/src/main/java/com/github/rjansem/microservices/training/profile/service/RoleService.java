package com.github.rjansem.microservices.training.profile.service;

import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.apisecurity.Authority;
import com.github.rjansem.microservices.training.exception.NOBCException;
import com.github.rjansem.microservices.training.profile.client.RoleClient;
import com.github.rjansem.microservices.training.profile.domain.efs.Role;
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
 * Services associés aux 'services' ({@link Role}) d'un utilisateur
 *
 * @author jntakpe
 */
@Service
public class RoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleService.class);

    private static final String ROLES_CACHE_NAME = "rolesCache";

    private final RoleClient roleClient;

    private final Cache rolesCache;

    @Autowired
    public RoleService(RoleClient roleClient, RedisCacheManager redisCacheManager) {
        this.roleClient = roleClient;
        this.rolesCache = redisCacheManager.getCache(ROLES_CACHE_NAME);
    }

    Observable<Set<Authority>> findAuthoritiesByLogin(AuthenticatedUser user) {
        return findByLogin(user)
                .map(roles -> roles.stream()
                        .map(Role::getLibelle)
                        .filter(a -> Authority.TRANSACTION.equals(a) || Authority.CONSULTATION.equals(a))
                        .collect(Collectors.toSet()));
    }

    private Observable<Set<Role>> findByLogin(AuthenticatedUser user) {
        Objects.requireNonNull(user);
        String login = Objects.requireNonNull(user.getLogin());
        LOGGER.info("Recherche des services pour l'utilisateur {}", login);
        Observable<Set<Role>> rolesFromCache = rolesFromCache(login);
        Observable<Set<Role>> rolesFromEfs = rolesFromEfs(user, login);
        return Observable.concat(rolesFromCache, rolesFromEfs)
                .first();
    }

    private Observable<Set<Role>> rolesFromEfs(AuthenticatedUser user, String login) {
        return roleClient.findByLogin(login, user.getToken())
                .doOnNext(roles -> LOGGER.info("Récupération de {} service(s) pour l'utilisateur {}", roles.size(), login))
                .doOnNext(roles -> cacheRoles(login, roles))
                .doOnError(err -> {
                    String msg = String.format("Impossible de récupérer les services de l'utilisateur %s", login);
                    NOBCException.throwEfsError(msg, err);
                });
    }

    private Observable<Set<Role>> rolesFromCache(String login) {
        return Optional.ofNullable(rolesCache.get(login, Set.class))
                .map(r -> createObs(r, login))
                .orElse(Observable.empty());
    }

    private Observable<Set<Role>> createObs(Set roles, String login) {
        LOGGER.info("Services de l'utilisateur {} récupérées depuis le cache", login);
        return Observable.just((Set<Role>) roles);
    }

    private void cacheRoles(String login, Set<Role> roles) {
        LOGGER.info("Ajout des services de l'utilisateur {} au cache", login);
        rolesCache.put(login, roles);
    }
}
