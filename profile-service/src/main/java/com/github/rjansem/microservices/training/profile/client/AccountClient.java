package com.github.rjansem.microservices.training.profile.client;

import com.github.rjansem.microservices.training.profile.domain.pib.Account;
import com.github.rjansem.microservices.training.apisecurity.AccountFeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rx.Observable;

import java.util.List;

/**
 * Client gérant les manipulations du bean {@link Account}
 *
 * @author rjansem
 */
@AccountFeignClient
public interface AccountClient {

    @RequestMapping(value = ClientConstants.ACCOUNT_BY_RACINE, method = RequestMethod.GET)
    Observable<List<Account>> findAccountsByRacine(@PathVariable(ClientConstants.USER_ID_PATHVAR) String userId, @PathVariable(ClientConstants.CLIENTS_TECHNICAL_ID_PATHVAR) String codeRacine);

}
