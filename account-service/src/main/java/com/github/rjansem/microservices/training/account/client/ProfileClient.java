package com.github.rjansem.microservices.training.account.client;

import com.github.rjansem.microservices.training.account.domain.efs.racine.Racine;
import com.github.rjansem.microservices.training.apisecurity.ProfileFeignClient;
import com.github.rjansem.microservices.training.apisecurity.SecurityConstants;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rx.Observable;

/**
 * Client pour le service profile-service
 *
 * @author bouhamyd
 */
@ProfileFeignClient
public interface ProfileClient {

    @RequestMapping(value = ClientConstants.RACINE_BY_ID, method = RequestMethod.GET)
    Observable<Racine> findRacineById(@PathVariable(ClientConstants.ID_RACINE) String codeRacine,
                                                  @RequestHeader(SecurityConstants.AUTHORIZATION_HEADER) String bearerToken);

}
