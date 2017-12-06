package com.github.rjansem.microservices.training.profile.api;

import com.github.rjansem.microservices.training.profile.domain.pib.ClientList;
import com.github.rjansem.microservices.training.profile.service.ClientService;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.profile.domain.pib.ClientList;
import com.github.rjansem.microservices.training.profile.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rx.Single;

/**
 * Ressource permettant de récupérer les informations relatives aux clients
 *
 * @author rjansem
 */
@RestController
@RequestMapping(ApiConstants.CLIENT)
public class ClientResource {

    private final ClientService clientService;

    @Autowired
    public ClientResource(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Single<ClientList> findClients(@PathVariable String userId) {
        return clientService.findClientsWithAccountsByLogin(userId).toSingle();
    }

}
