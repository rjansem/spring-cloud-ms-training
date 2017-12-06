package com.github.rjansem.microservices.training.transaction.api;

import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.ListOfTransaction;
import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.SignTransaction;
import com.github.rjansem.microservices.training.transaction.service.OrderService;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.apisecurity.ServicesUris;
import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.ListOfTransaction;
import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.SignTransaction;
import com.github.rjansem.microservices.training.transaction.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rx.Single;

import javax.validation.Valid;

/**
 * Ressource REST gérant la signature des ordres
 *
 * @author aazzerrifi
 */
@Validated
@RestController
@RequestMapping(value = ServicesUris.API + ApiConstants.SIGN_PAYMENT)
public class SignatureResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionResource.class);

    private final OrderService orderService;

    @Autowired
    public SignatureResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public Single<ListOfTransaction> signTransactionByKbv(@PathVariable String userId, @Valid @RequestBody SignTransaction signTransaction) {

        return orderService.signPayment(signTransaction, userId)
                .doOnNext(p -> LOGGER.info("[ANALYTICS] [{}] [Transaction] [SignByVkb] transactionId={}",
                        userId,
                        signTransaction.getTransactionIds().toString()
                ))
                .toSingle();
    }
}
