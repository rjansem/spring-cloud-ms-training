package com.github.rjansem.microservices.training.transaction.client;

import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.CompteOrdre;
import com.github.rjansem.microservices.training.apisecurity.EfsFeignClient;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.CompteOrdre;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import rx.Observable;

import java.util.Set;

import static com.github.rjansem.microservices.training.apisecurity.SecurityConstants.AUTHORIZATION_HEADER;
import static com.github.rjansem.microservices.training.transaction.client.ClientConstants.*;

/**
 * Repository gérant les manipulations relatives aux comptes emetteurs
 *
 * @author mbouhamyd
 */
@EfsFeignClient
public interface CompteOrdreClient {

    @RequestMapping(value = COMPTES_EMETTEUR, method = RequestMethod.GET)
    Observable<Set<CompteOrdre>> findComptesEmetteurs(@RequestParam(LOGIN_PARAM) String login,
                                                      @RequestParam(CODE_APPLICATION) String codeApplication,
                                                      @RequestHeader(AUTHORIZATION_HEADER) String bearerToken);
}
