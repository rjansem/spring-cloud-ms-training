package com.github.rjansem.microservices.training.transaction.client;


import com.github.rjansem.microservices.training.apisecurity.EfsFeignClientWSauthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import rx.Observable;

import static com.github.rjansem.microservices.training.transaction.client.ClientConstants.VKB;
/**
 * Repository gérant les manipulations relatives aux VKB
 *
 * @author aazzerrifi
 */
@EfsFeignClientWSauthentication
public interface GetVkbClient {

    @RequestMapping(value = VKB, method = RequestMethod.GET)
    Observable<String> getVKB(@RequestParam("dim") String dim);
}


