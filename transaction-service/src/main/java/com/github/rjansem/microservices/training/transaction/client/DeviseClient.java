package com.github.rjansem.microservices.training.transaction.client;

import com.github.rjansem.microservices.training.transaction.domain.efs.devise.Devise;
import com.github.rjansem.microservices.training.apisecurity.EfsFeignClient;
import com.github.rjansem.microservices.training.transaction.domain.efs.devise.Devise;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rx.Observable;

import java.util.Set;

import static com.github.rjansem.microservices.training.apisecurity.SecurityConstants.AUTHORIZATION_HEADER;
import static com.github.rjansem.microservices.training.transaction.client.ClientConstants.*;

/**
 * Repository gérant les manipulations des devises
 *
 * @author mbouhamyd
 */
@EfsFeignClient
public interface DeviseClient {

    @RequestMapping(value = DEVISE, method = RequestMethod.GET)
    Observable<Devise> findDeviseById(@PathVariable(DEVISE_ID) String id, @RequestHeader(AUTHORIZATION_HEADER) String bearerToken);

    @RequestMapping(value = DEVISE_TRANSACTIONS, method = RequestMethod.GET)
    Observable<Set<Devise>> findDeviseTransactionById(@RequestHeader(AUTHORIZATION_HEADER) String bearerToken);
}

