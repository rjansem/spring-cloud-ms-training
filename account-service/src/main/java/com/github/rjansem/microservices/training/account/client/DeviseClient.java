package com.github.rjansem.microservices.training.account.client;

import com.github.rjansem.microservices.training.account.domain.efs.devise.Devise;
import com.github.rjansem.microservices.training.apisecurity.EfsFeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rx.Observable;

import static com.github.rjansem.microservices.training.account.client.ClientConstants.DEVISE;
import static com.github.rjansem.microservices.training.account.client.ClientConstants.ID;

/**
 * Repository gérant les manipulations des devises
 *
 * @author mbouhamyd
 */
@EfsFeignClient
public interface DeviseClient {

    @RequestMapping(value = DEVISE, method = RequestMethod.GET)
    Observable<Devise> findDeviseById(@PathVariable(ID) String id);
}

