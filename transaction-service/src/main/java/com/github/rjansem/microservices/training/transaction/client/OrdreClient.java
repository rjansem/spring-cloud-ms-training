package com.github.rjansem.microservices.training.transaction.client;

import com.github.rjansem.microservices.training.transaction.domain.efs.ObjectVide;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.EmissionOrdre;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.Ordre;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.OrdreDetail;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.SignatureKBV;
import com.github.rjansem.microservices.training.transaction.domain.efs.post.PostTransaction;
import com.github.rjansem.microservices.training.apisecurity.EfsFeignClient;
import com.github.rjansem.microservices.training.transaction.domain.efs.ObjectVide;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.EmissionOrdre;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.Ordre;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.OrdreDetail;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.SignatureKBV;
import com.github.rjansem.microservices.training.transaction.domain.efs.post.PostTransaction;
import org.springframework.web.bind.annotation.*;
import rx.Observable;

import java.util.List;

import static com.github.rjansem.microservices.training.apisecurity.SecurityConstants.AUTHORIZATION_HEADER;
import static com.github.rjansem.microservices.training.transaction.client.ClientConstants.*;


/**
 * Repository gérant les manipulations relatives aux comptes emetteurs
 *
 * @author mbouhamyd
 */
@EfsFeignClient
public interface OrdreClient {

    @RequestMapping(value = ORDRES, method = RequestMethod.GET)
    Observable<List<Ordre>> findAllOrdres(@RequestParam(LOGIN_PARAM) String login,
                                          @RequestParam(CODES_APPLICATIONS) String codeApplication,
                                          @RequestHeader(AUTHORIZATION_HEADER) String bearerToken);

    @RequestMapping(value = ORDRES_DETAIL, method = RequestMethod.GET)
    Observable<OrdreDetail> findDetailOrdreById(@PathVariable(ID_ORDRE) String id,
                                                @RequestParam(LOGIN_PARAM) String login,
                                                @RequestHeader(AUTHORIZATION_HEADER) String bearerToken);

    @RequestMapping(value = ORDRES_DETAIL, method = RequestMethod.GET)
    Observable<OrdreDetail> findDetailOrdreById(@PathVariable(ID_ORDRE) String id,
                                                @RequestParam(LOGIN_PARAM) String login,
                                                @RequestParam(CODE_APPLICATION) String codeApplication,
                                                @RequestHeader(AUTHORIZATION_HEADER) String bearerToken);

    @RequestMapping(value = TRANSACTIONS, method = RequestMethod.POST, produces = "application/json")
    Observable<EmissionOrdre> emissionTransaction(@RequestBody PostTransaction postTransaction,
                                                  @RequestHeader(AUTHORIZATION_HEADER) String bearerToken);

    @RequestMapping(value = SIGNE_KBV, method = RequestMethod.PUT, produces = "application/json")
    Observable<ObjectVide> signeKbv(@PathVariable(ID_ORDRE) String id,
                                    @RequestBody SignatureKBV signatureKBV,
                                    @RequestHeader(AUTHORIZATION_HEADER) String bearerToken);
}