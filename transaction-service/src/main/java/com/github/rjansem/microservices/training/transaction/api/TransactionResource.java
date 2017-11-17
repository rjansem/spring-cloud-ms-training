package com.github.rjansem.microservices.training.transaction.api;

import com.github.rjansem.microservices.training.transaction.domain.pbi.PaymentOrder;
import com.github.rjansem.microservices.training.transaction.service.MeansSignatureService;
import com.github.rjansem.microservices.training.transaction.service.OrderService;
import com.github.rjansem.microservices.training.transaction.validator.PaymentOrderValidator;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.apisecurity.ServicesUris;
import com.github.rjansem.microservices.training.transaction.domain.pbi.PaymentOrder;
import com.github.rjansem.microservices.training.transaction.service.MeansSignatureService;
import com.github.rjansem.microservices.training.transaction.service.OrderService;
import com.github.rjansem.microservices.training.transaction.validator.PaymentOrderValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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

    private final MeansSignatureService meansSignatureService;

    @Value("${business.operations.defaultPageSize}")
    private Integer defaultPageSize;

    @Value("${business.links.externalBaseUrl}")
    private String externalBaseUrl;

    @Autowired
    public TransactionResource(OrderService orderService, PaymentOrderValidator paymentOrderValidator, MeansSignatureService meansSignatureService) {
        this.orderService = orderService;
        this.paymentOrderValidator = paymentOrderValidator;
        this.meansSignatureService = meansSignatureService;
    }

    @RequestMapping(value = PAYMENT_ORDER, method = RequestMethod.POST, headers = "Accept=application/json")
    public Single<HttpEntity<PagedResources<PaymentOrder>>> issuanceOrder(@Valid @RequestBody PaymentOrder paymentOrder,
                                                                          @AuthenticationPrincipal AuthenticatedUser user) {
        paymentOrderValidator.validate(paymentOrder);
        meansSignatureService.checkMeansOfSignature(user, paymentOrder).toBlocking().first();
        return orderService.initiateSEPAPaymentOrder(paymentOrder, user)
                .doOnNext(p -> LOGGER.info("[ANALYTICS] [{}] [Transaction] [NewTransfer] transactionId={} totalAmount={} paymentMode={} currency={} internal={}",
                        user.getLogin(),
                        p.getId(),
                        p.getTransactionInfo().getInstructedAmount(),
                        p.getTransactionInfo().getPaymentMode(),
                        p.getTransactionInfo().getInstructedCurrency(),
                        p.checkInternal()
                ))
                .toList()
                .map(paymentOrders -> pagingInitiateSEPA(user, paymentOrders))
                .toSingle();
    }

    private HttpEntity<PagedResources<PaymentOrder>> pagingInitiateSEPA(AuthenticatedUser user, List<PaymentOrder> result) {
        PagedResources<PaymentOrder> resources = new PagedResources<>(result, null);
        // FIXME à refaire remplacer le new link par linkTo
        Link link = new Link(externalBaseUrl + "/nobc-api/transaction-service/v1.1/signTransactionByKbv")
                .withRel(SIGN_BY_KBV_CONNETION_CODE_LINK);
        // Link link = linkTo(methodOn(SigniatureResource.class).signTransactionByKbv(null, user)).withRel(SIGN_BY_KBV_CONNETION_CODE_LINK);
        resources.add(link);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }
}
