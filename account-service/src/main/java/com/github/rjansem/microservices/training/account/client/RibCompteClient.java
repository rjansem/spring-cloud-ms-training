package com.github.rjansem.microservices.training.account.client;

import com.github.rjansem.microservices.training.account.domain.efs.compte.RibCompte;
import com.github.rjansem.microservices.training.apisecurity.EfsFeignClient;
import org.springframework.web.bind.annotation.*;
import rx.Observable;

import static com.github.rjansem.microservices.training.account.client.ClientConstants.*;

/**
 * Repository gérant les manipulations relatives aux ribs
 *
 * @author rjansem
 */
@EfsFeignClient
public interface RibCompteClient {

    @RequestMapping(value = RIB, method = RequestMethod.GET)
    Observable<RibCompte> findRibByLoginAndRacine(@PathVariable(IBAN_PARAM) String iban,
                                                  @RequestParam(LOGIN_PARAM) String login,
                                                  @RequestParam(CODE_RACINE_PARAM) String codeRacine);
}