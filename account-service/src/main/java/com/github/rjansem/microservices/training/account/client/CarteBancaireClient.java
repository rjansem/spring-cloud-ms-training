package com.github.rjansem.microservices.training.account.client;

import com.github.rjansem.microservices.training.account.domain.efs.cartebancaire.CarteBancaire;
import com.github.rjansem.microservices.training.apisecurity.EfsFeignClient;
import org.springframework.web.bind.annotation.*;
import rx.Observable;

import java.util.Set;

import static com.github.rjansem.microservices.training.account.client.ClientConstants.*;
import static com.github.rjansem.microservices.training.apisecurity.SecurityConstants.AUTHORIZATION_HEADER;

/**
 * Repository gérant les manipulations relatives aux cartes bancaires
 *
 * @author mbouhamyd
 */
@EfsFeignClient
public interface CarteBancaireClient {

    @RequestMapping(value = CARTE_BANCAIRE_DETAIL, method = RequestMethod.GET)
    Observable<CarteBancaire> findCarteBancaireDetailById(@PathVariable(ID) String id,
                                                          @RequestParam(LOGIN_PARAM) String login,
                                                          @RequestParam(CODE_RACINE_PARAM) String codeRacine,
                                                          @RequestHeader(AUTHORIZATION_HEADER) String bearerToken);

    @RequestMapping(value = CARTE_BANCAIRE_IBAN, method = RequestMethod.GET)
    Observable<Set<CarteBancaire>> findCarteBancaireByIban(@PathVariable(IBAN) String iban,
                                                           @RequestParam(LOGIN_PARAM) String login,
                                                           @RequestParam(CODE_RACINE_PARAM) String codeRacine,
                                                           @RequestHeader(AUTHORIZATION_HEADER) String bearerToken);

}