package com.github.rjansem.microservices.training.transaction.api;

import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.Ordre;
import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.Transaction;
import com.github.rjansem.microservices.training.transaction.mapper.commun.PaginationUtils;
import com.github.rjansem.microservices.training.transaction.service.TransactionService;
import com.github.rjansem.microservices.training.transaction.util.LinksUtils;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.apisecurity.ServicesUris;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.Ordre;
import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.Transaction;
import com.github.rjansem.microservices.training.transaction.mapper.commun.PaginationUtils;
import com.github.rjansem.microservices.training.transaction.service.TransactionService;
import com.github.rjansem.microservices.training.transaction.util.LinksUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.UriTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.rjansem.microservices.training.transaction.api.ApiConstants.TRANSACTION_OVERWIEW;
import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Ressource REST gérant le détail des transactions
 *
 * @author aazzerrifi
 * @author mbouhamyd
 */
@Validated
@RestController
@RequestMapping(ServicesUris.API + TRANSACTION_OVERWIEW)
public class OverviewResource {

    private final TransactionService transactionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(OverviewResource.class);

    @Value("${business.operations.defaultPageSize}")
    private Integer defaultPageSize;

    @Value("${business.links.externalBaseUrl}")
    private String externalBaseUrl;

    @Autowired
    public OverviewResource(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<PagedResources<Transaction>> findAllTransactions(@AuthenticationPrincipal AuthenticatedUser user,
                                                                           @RequestParam(required = false, defaultValue = "1") Integer page,
                                                                           @RequestParam(required = false,defaultValue = "20") Integer size) {


        return pagingFindAllTransactions(user, page, size, transactionService.findAllOrdres(user));
    }

    private ResponseEntity<PagedResources<Transaction>> pagingFindAllTransactions(AuthenticatedUser user, Integer page, Integer size,
                                                                                  List<Ordre> ordres) {
        int pageSize = size == null ? defaultPageSize : size;
        List<Ordre> ordresPage = PaginationUtils.paginate(ordres, page, pageSize);
        PagedResources.PageMetadata pageMetadata = PaginationUtils.generatePaginationMetadata(ordres, page, pageSize);

        List<Transaction> transactionsPageWithDetail = transactionService.findTransactions(user ,ordresPage)
                .doOnNext(transactions -> LOGGER.debug("[ANALYTICS] [{}] [Transaction] [Overview] page={} pageSize={}", user.getLogin(), page, pageSize))
                .toBlocking()
                .single()
                .stream()
                .sorted()
                .collect(Collectors.toList()
        );

        PagedResources<Transaction> resources = new PagedResources<>(transactionsPageWithDetail, pageMetadata);
        if (resources.getMetadata().getNumber() < resources.getMetadata().getTotalPages()) {
            resources.add(linkTo(methodOn(OverviewResource.class)
                    .findAllTransactions(user, page + 1, size)).withRel(Link.REL_NEXT));
        }
        if (resources.getMetadata().getNumber() > 1) {
            resources.add(linkTo(methodOn(OverviewResource.class)
                    .findAllTransactions(user, page - 1, size)).withRel(Link.REL_PREVIOUS));
        }
        List<Link> linksWithUpdatedDomain = resources.getLinks().stream()
                .map(l -> new Link(new UriTemplate(LinksUtils.changeHostname(l.getHref(), externalBaseUrl)), l.getRel()))
                .collect(Collectors.toList());
        resources.removeLinks();
        resources.add(linksWithUpdatedDomain);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }
}
