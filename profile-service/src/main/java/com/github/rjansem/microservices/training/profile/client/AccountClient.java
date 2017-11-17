package com.github.rjansem.microservices.training.profile.client;

import com.github.rjansem.microservices.training.profile.domain.pib.Account;
import com.github.rjansem.microservices.training.apisecurity.AccountFeignClient;
import com.github.rjansem.microservices.training.apisecurity.SecurityConstants;
import com.github.rjansem.microservices.training.profile.domain.pib.Account;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rx.Observable;

import java.util.List;

/**
 * Client gérant les manipulations du bean {@link Account}
 *
 * @author jntakpe
 */
@AccountFeignClient
public interface AccountClient {

    @RequestMapping(value = ClientConstants.ACCOUNT_BY_RACINE, method = RequestMethod.GET)
    Observable<List<Account>> findAccountsByRacine(@PathVariable(ClientConstants.CLIENTS_TECHNICAL_ID_PATHVAR) String codeRacine,
                                                   @RequestHeader(SecurityConstants.AUTHORIZATION_HEADER) String bearerToken);

}
