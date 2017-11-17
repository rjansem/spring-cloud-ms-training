package com.github.rjansem.microservices.training.profile.client;

import com.github.rjansem.microservices.training.profile.domain.efs.Role;
import com.github.rjansem.microservices.training.apisecurity.EfsFeignClient;
import com.github.rjansem.microservices.training.apisecurity.SecurityConstants;
import com.github.rjansem.microservices.training.profile.domain.efs.Role;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import rx.Observable;

import java.util.Set;

/**
 * Client gérant les manipulations du {@link Role} représentant les 'services' d'un utilisateur
 *
 * @author jntakpe
 */
@EfsFeignClient
public interface RoleClient {

    @RequestMapping(value = ClientConstants.SERVICE, method = RequestMethod.GET)
    Observable<Set<Role>> findByLogin(@RequestParam(ClientConstants.LOGIN_PARAM) String login,
                                      @RequestHeader(SecurityConstants.AUTHORIZATION_HEADER) String bearerToken);

}
