package com.github.rjansem.microservices.training.profile.client;

import com.github.rjansem.microservices.training.profile.domain.efs.Abonne;
import com.github.rjansem.microservices.training.profile.domain.efs.Racine;
import com.github.rjansem.microservices.training.apisecurity.EfsFeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import rx.Observable;

import java.util.Set;

/**
 * Client gérant les manipulations du bean {@link Racine}
 *
 * @author rjansem
 */
@EfsFeignClient
public interface RacineClient {

    @RequestMapping(value = ClientConstants.RACINE, method = RequestMethod.GET)
    Observable<Set<Racine>> findByLogin(@RequestParam(ClientConstants.LOGIN_PARAM) String login);

    @RequestMapping(value = ClientConstants.ABONNE, method = RequestMethod.GET)
    Observable<Abonne> findAbonne(@RequestParam(ClientConstants.LOGIN_PARAM) String login);

}
