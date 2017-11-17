package com.github.rjansem.microservices.training.transaction.client;

import com.github.rjansem.microservices.training.transaction.domain.efs.beneficiaires.BeneficiaireBook;
import com.github.rjansem.microservices.training.apisecurity.EfsFeignClient;
import com.github.rjansem.microservices.training.transaction.domain.efs.beneficiaires.BeneficiaireBook;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import rx.Observable;

import static com.github.rjansem.microservices.training.apisecurity.SecurityConstants.AUTHORIZATION_HEADER;
import static com.github.rjansem.microservices.training.transaction.client.ClientConstants.*;

/**
 * Repository gérant les manipulations relatives aux comptes beneficiares
 *
 * @author aazzerrifi
 */
@EfsFeignClient
public interface BeneficiaireClient {

    @RequestMapping(value = COMPTES_BENEF, method = RequestMethod.GET)
    Observable<BeneficiaireBook> findComptesBeneficiares(@RequestParam(LOGIN_PARAM) String login,
                                                         @RequestParam(CODE_APPLICATION) String codeApplication,
                                                         @RequestHeader(AUTHORIZATION_HEADER) String bearerToken);
}
