package com.github.rjansem.microservices.training.account.client;

import com.github.rjansem.microservices.training.account.domain.efs.operation.OperationCarte;
import com.github.rjansem.microservices.training.account.domain.efs.operation.OperationCompte;
import com.github.rjansem.microservices.training.apisecurity.EfsFeignClient;
import org.springframework.web.bind.annotation.*;
import rx.Observable;

import java.util.Set;

import static com.github.rjansem.microservices.training.account.client.ClientConstants.*;
import static com.github.rjansem.microservices.training.apisecurity.SecurityConstants.AUTHORIZATION_HEADER;

/**
 * Repository gérant les manipulations relatives aux opérations d'un compte
 *
 * @author mbouhamyd
 */
@EfsFeignClient
public interface OperationClient {

    @RequestMapping(value = OPERATIONS_IBAN, method = RequestMethod.GET)
    Observable<Set<OperationCompte>> findOperationsByIban(@PathVariable(IBAN) String iban,
                                                          @RequestParam(LOGIN_PARAM) String login,
                                                          @RequestParam(CODE_RACINE_PARAM) String codeRacine,
                                                          @RequestHeader(AUTHORIZATION_HEADER) String bearerToken);

    @RequestMapping(value = OPERATIONS_CARTEBANCAIRE, method = RequestMethod.GET)
    Observable<Set<OperationCarte>> findOperationsByIdCard(@PathVariable(ID) String id,
                                                           @RequestParam(LOGIN_PARAM) String login,
                                                           @RequestParam(CODE_RACINE_PARAM) String codeRacine,
                                                           @RequestHeader(AUTHORIZATION_HEADER) String bearerToken);
}

