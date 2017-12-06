package com.github.rjansem.microservices.training.transaction.api;

import com.github.rjansem.microservices.training.apisecurity.ServicesUris;
import com.github.rjansem.microservices.training.transaction.domain.pbi.PaymentOrder;
import com.github.rjansem.microservices.training.transaction.service.OrderService;
import com.github.rjansem.microservices.training.transaction.validator.PaymentOrderValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rx.Single;

import javax.validation.Valid;
import java.util.List;

import static com.github.rjansem.microservices.training.transaction.api.ApiConstants.PAYMENT_ORDER;

/**
 * Ressource REST gérant l'initialisation des ordres
 *
 * @author aazzerrifi
 */
@Validated
@RestController
@RequestMapping(ServicesUris.API)
public class TransactionResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionResource.class);

    public static final String SIGN_BY_KBV_CONNETION_CODE_LINK = "signByKbvConnetionCode";

    private final OrderService orderService;

    private final PaymentOrderValidator paymentOrderValidator;

    @Value("${business.operations.defaultPageSize}")
    private Integer defaultPageSize;

    @Value("${business.links.externalBaseUrl}")
    private String externalBaseUrl;

    @Autowired
    public TransactionResource(OrderService orderService, PaymentOrderValidator paymentOrderValidator) {
        this.orderService = orderService;
        this.paymentOrderValidator = paymentOrderValidator;
    }

    @RequestMapping(value = PAYMENT_ORDER, method = RequestMethod.POST, headers = "Accept=application/json")
    public Single<List<PaymentOrder>> issuanceOrder(@PathVariable String userId, @Valid @RequestBody PaymentOrder paymentOrder) {
        paymentOrderValidator.validate(paymentOrder);
        return orderService.initiateSEPAPaymentOrder(paymentOrder, userId)
                .doOnNext(p -> LOGGER.info("[ANALYTICS] [{}] [Transaction] [NewTransfer] transactionId={} totalAmount={} paymentMode={} currency={} internal={}",
                        userId,
                        paymentOrder.getId(),
                        paymentOrder.getTransactionInfo().getInstructedAmount(),
                        paymentOrder.getTransactionInfo().getPaymentMode(),
                        paymentOrder.getTransactionInfo().getInstructedCurrency(),
                        paymentOrder.checkInternal()
                ))
                .toList()
                .toSingle();
    }

}
