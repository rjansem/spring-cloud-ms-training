package com.github.rjansem.microservices.training.account.client;

import com.github.rjansem.microservices.training.account.domain.efs.credit.Credit;
import com.github.rjansem.microservices.training.account.domain.efs.credit.EngagementSignature;
import com.github.rjansem.microservices.training.apisecurity.EfsFeignClient;
import org.springframework.web.bind.annotation.*;
import rx.Observable;

import java.util.Set;

import static com.github.rjansem.microservices.training.account.client.ClientConstants.*;
import static com.github.rjansem.microservices.training.apisecurity.SecurityConstants.AUTHORIZATION_HEADER;

/**
 * Repository gérant les manipulations relatives aux comptes crédits
 *
 * @author rjansem
 */
@EfsFeignClient
public interface CreditClient {

    @RequestMapping(value = CREDITS, method = RequestMethod.GET)
    Observable<Set<Credit>> findCreditByLoginAndRacine(@RequestParam(LOGIN_PARAM) String login,
                                                       @RequestParam(CODE_RACINE_PARAM) String codeRacine,
                                                       @RequestHeader(AUTHORIZATION_HEADER) String bearerToken);

    @RequestMapping(value = CREDITS_DETAIL, method = RequestMethod.GET)
    Observable<Credit> findDetailCreditByLoginAndRacine(@PathVariable(NUM_PARAM) String numero,
                                                        @RequestParam(LOGIN_PARAM) String login,
                                                        @RequestParam(CODE_RACINE_PARAM) String codeRacine,
                                                        @RequestHeader(AUTHORIZATION_HEADER) String bearerToken);

    @RequestMapping(value = ENGAGEMENTS_PAR_SIGNATURE, method = RequestMethod.GET)
    Observable<Set<EngagementSignature>> findEngagementSignatureByLoginAndRacine(@RequestParam(LOGIN_PARAM) String login,
                                                                                 @RequestParam(CODE_RACINE_PARAM) String codeRacine,
                                                                                 @RequestHeader(AUTHORIZATION_HEADER) String bearerToken);

    @RequestMapping(value = ENGAGEMENTS_PAR_SIGNATURE_DETAIL, method = RequestMethod.GET)
    Observable<EngagementSignature> findDetailEngagementSignatureByLoginAndRacine(@PathVariable(NUM_PARAM) String numero,
                                                                                  @RequestParam(LOGIN_PARAM) String login,
                                                                                  @RequestParam(CODE_RACINE_PARAM) String codeRacine,
                                                                                  @RequestHeader(AUTHORIZATION_HEADER) String bearerToken);
}