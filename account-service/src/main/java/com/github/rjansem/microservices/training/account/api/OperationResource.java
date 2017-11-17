package com.github.rjansem.microservices.training.account.api;

import com.github.rjansem.microservices.training.account.util.AccountTypeValidator;
import com.github.rjansem.microservices.training.account.util.LinksUtils;
import com.github.rjansem.microservices.training.account.util.PaginationUtils;
import com.github.rjansem.microservices.training.account.domain.pbi.operation.Operation;
import com.github.rjansem.microservices.training.account.service.OperationService;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.UriTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import rx.Observable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Resource pour la gestion des opérations
 *
 * @author rjansem
 */
@RestController
@RequestMapping(ApiConstants.CLIENTS_TECHNICAL_BY_ID)
public class OperationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperationResource.class);

    private static final String CARTE_BANCAIRE_PARAM = "CC";

    private static final String All_CARTE_BANCAIRE_PARAM = "ALLCC";

    @Value("${business.operations.defaultPageSize}")
    private Integer defaultPageSize;

    @Value("${business.links.externalBaseUrl}")
    private String externalBaseUrl;

    private OperationService operationService;

    private AccountTypeValidator accountTypeValidator;

    @Autowired
    public OperationResource(OperationService operationService, AccountTypeValidator accountTypeValidator) {
        this.operationService = operationService;
        this.accountTypeValidator = accountTypeValidator;
    }

    @RequestMapping(method = RequestMethod.GET, value = ApiConstants.OPERATIONS, headers = "Accept=application/json")
    public HttpEntity<PagedResources<Operation>> findOperations(@PathVariable String clientTechnicalId,
                                                                @RequestParam String productType,
                                                                @RequestParam String productId,
                                                                @RequestParam(required = false, defaultValue = "1") Integer page,
                                                                @RequestParam(required = false) Integer size,
                                                                @AuthenticationPrincipal AuthenticatedUser user) {
        Observable<Set<Operation>> operationsObs;
        if (productType.equalsIgnoreCase(CARTE_BANCAIRE_PARAM)) {
            operationsObs = operationService.accountOperationByIdCard(productId, clientTechnicalId, user);
        } else if (productType.equals(All_CARTE_BANCAIRE_PARAM)) {
            operationsObs = operationService.accountOperationForAllCreditCardsByIban(productId, clientTechnicalId, user);
        } else {
            accountTypeValidator.validate(productType);
            operationsObs = operationService.accountOperationByIban(productId, productType, clientTechnicalId, user);
        }
        Set<Operation> ops = operationsObs.toList().toBlocking().single().stream()
                .findFirst()
                .orElse(Collections.emptySet());
        List<Operation> operations = new ArrayList<>(ops).stream().sorted().collect(Collectors.toList());
        int pageSize = size == null ? defaultPageSize : size;
        List<Operation> operationsPage = PaginationUtils.paginate(operations, page, pageSize);
        PagedResources.PageMetadata pageMetadata = PaginationUtils.generatePaginationMetadata(operations, page, pageSize);
        PagedResources<Operation> resources = new PagedResources<>(operationsPage, pageMetadata);
        if (resources.getMetadata().getNumber() < resources.getMetadata().getTotalPages()) {
            resources.add(linkTo(methodOn(OperationResource.class)
                    .findOperations(clientTechnicalId, productType, productId, page + 1, size, user)).withRel(Link.REL_NEXT));
        }
        if (resources.getMetadata().getNumber() > 1) {
            resources.add(linkTo(methodOn(OperationResource.class)
                    .findOperations(clientTechnicalId, productType, productId, page - 1, size, user)).withRel(Link.REL_PREVIOUS));
        }

        List<Link> linksWithUpdatedDomain = resources.getLinks().stream().map(l -> new Link(new UriTemplate(LinksUtils.changeHostname(l.getHref(), externalBaseUrl)), l.getRel())).collect(Collectors.toList());
        resources.removeLinks();
        resources.add(linksWithUpdatedDomain);

        LOGGER.info("[ANALYTICS] [{}] [Account] [Operations] page={} pageSize={}", user.getLogin(), page, size);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

}