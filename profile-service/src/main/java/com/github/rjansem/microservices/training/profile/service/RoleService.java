package com.github.rjansem.microservices.training.profile.service;

import com.github.rjansem.microservices.training.apisecurity.Authority;
import com.github.rjansem.microservices.training.exception.APIException;
import com.github.rjansem.microservices.training.profile.client.RoleClient;
import com.github.rjansem.microservices.training.profile.domain.efs.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Services associés aux 'services' ({@link Role}) d'un utilisateur
 *
 * @author rjansem
 */
@Service
public class RoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleService.class);

    private final RoleClient roleClient;

    @Autowired
    public RoleService(RoleClient roleClient) {
        this.roleClient = roleClient;
    }

    Observable<Set<Authority>> findAuthoritiesByLogin(String userId) {
        return findByLogin(userId)
                .map(roles -> roles.stream()
                        .map(Role::getLibelle)
                        .filter(a -> Authority.TRANSACTION.equals(a) || Authority.CONSULTATION.equals(a))
                        .collect(Collectors.toSet()));
    }

    private Observable<Set<Role>> findByLogin(String userId) {
        Objects.requireNonNull(userId);
        String login = Objects.requireNonNull(userId);
        LOGGER.info("Recherche des services pour l'utilisateur {}", login);
        return rolesFromEfs(userId, login);
    }

    private Observable<Set<Role>> rolesFromEfs(String userId, String login) {
        return roleClient.findByLogin(login)
                .doOnNext(roles -> LOGGER.info("Récupération de {} service(s) pour l'utilisateur {}", roles.size(), login))
                .doOnError(err -> {
                    String msg = String.format("Impossible de récupérer les services de l'utilisateur %s", login);
                    APIException.throwEfsError(msg, err);
                });
    }

}
