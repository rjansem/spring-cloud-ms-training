package com.github.rjansem.microservices.training.transaction.client;

import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.MoyenSignature;
import com.github.rjansem.microservices.training.apisecurity.EfsFeignClient;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.MoyenSignature;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import rx.Observable;

import java.util.Set;

import static com.github.rjansem.microservices.training.apisecurity.SecurityConstants.AUTHORIZATION_HEADER;
import static com.github.rjansem.microservices.training.transaction.client.ClientConstants.LOGIN_PARAM;
import static com.github.rjansem.microservices.training.transaction.client.ClientConstants.MOYENS_SIGN;


/**
 * Repository gérant les manipulations relatives aux comptes emetteurs
 *
 * @author aazzerrifi
 */
@EfsFeignClient
public interface AuthentificationClient {
    @RequestMapping(value = MOYENS_SIGN, method = RequestMethod.GET)
    Observable<Set<MoyenSignature>> findMoyensSignature(@RequestParam(LOGIN_PARAM) String login,
                                                        @RequestParam(name = "codeapplication", required = false) String codeapplication,
                                                        @RequestParam(name = "codeoperation", required = false) String codeoperation,
                                                        @RequestParam(name = "montant", required = false) String montant,
                                                        @RequestParam(name = "devise", required = false) String devise,
                                                        @RequestHeader(AUTHORIZATION_HEADER) String bearerToken);
}