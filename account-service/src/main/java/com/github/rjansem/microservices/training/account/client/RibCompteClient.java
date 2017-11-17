package com.github.rjansem.microservices.training.account.client;

import com.github.rjansem.microservices.training.account.domain.efs.compte.RibCompte;
import com.github.rjansem.microservices.training.apisecurity.EfsFeignClient;
import org.springframework.web.bind.annotation.*;
import rx.Observable;

import static com.github.rjansem.microservices.training.account.client.ClientConstants.*;
import static com.github.rjansem.microservices.training.apisecurity.SecurityConstants.AUTHORIZATION_HEADER;

/**
 * Repository gérant les manipulations relatives aux ribs
 *
 * @author aazzerrifi
 */
@EfsFeignClient
public interface RibCompteClient {

    @RequestMapping(value = RIB, method = RequestMethod.GET)
    Observable<RibCompte> findRibByLoginAndRacine(@PathVariable(IBAN_PARAM) String iban,
                                                  @RequestParam(LOGIN_PARAM) String login,
                                                  @RequestParam(CODE_RACINE_PARAM) String codeRacine,
                                                  @RequestHeader(AUTHORIZATION_HEADER) String bearerToken);
}