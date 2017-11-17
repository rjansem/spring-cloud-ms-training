package com.github.rjansem.microservices.training.account.client;

import com.github.rjansem.microservices.training.account.domain.efs.investissement.InvestissementTitre;
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
public interface InvestissementClient {


    @RequestMapping(value = PORTEFEUILLES_TITRES_TITRES, method = RequestMethod.GET)
    Observable<Set<InvestissementTitre>> findPortefeuillesTitresById(@PathVariable(NUMEMRO) String numero,
                                                                     @RequestParam(LOGIN_PARAM) String login,
                                                                     @RequestParam(CODE_RACINE_PARAM) String codeRacine,
                                                                     @RequestHeader(AUTHORIZATION_HEADER) String bearerToken);

    @RequestMapping(value = ASSURANCE_VIE_TITRES, method = RequestMethod.GET)
    Observable<Set<InvestissementTitre>> findLifeAssuranceTitresById(@PathVariable(NUMEMRO) String numero,
                                                                     @RequestParam(LOGIN_PARAM) String login,
                                                                     @RequestParam(CODE_RACINE_PARAM) String codeRacine,
                                                                     @RequestHeader(AUTHORIZATION_HEADER) String bearerToken);

    @RequestMapping(value = ASSURANCE_VIE_NOBC_TITRES, method = RequestMethod.GET)
    Observable<Set<InvestissementTitre>> findLifeAssuranceNOBCTitresById(@PathVariable(NUMEMRO) String numero,
                                                                         @RequestParam(LOGIN_PARAM) String login,
                                                                         @RequestParam(CODE_RACINE_PARAM) String codeRacine,
                                                                         @RequestHeader(AUTHORIZATION_HEADER) String bearerToken);
}

