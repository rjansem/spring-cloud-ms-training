package com.github.rjansem.microservices.training.profile.api;

import com.github.rjansem.microservices.training.profile.domain.efs.Racine;
import com.github.rjansem.microservices.training.profile.service.RacineService;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.profile.domain.efs.Racine;
import com.github.rjansem.microservices.training.profile.domain.pib.ClientList;
import com.github.rjansem.microservices.training.profile.service.ClientService;
import com.github.rjansem.microservices.training.profile.service.RacineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rx.Single;

/**
 * Ressource permettant de récupérer les informations relatives aux racines
 *
 * @author buohamyd
 */
@RestController
@RequestMapping(ApiConstants.RACINE_BY_ID)
public class RacineResource {

    private final RacineService racineService;

    @Autowired
    public RacineResource(RacineService racineService) {
        this.racineService = racineService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Single<Racine> findRacineById(@PathVariable String idRacine, @AuthenticationPrincipal String userId) {
        return racineService.findRacineById(userId,idRacine).toSingle();
    }

}
