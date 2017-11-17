package com.github.rjansem.microservices.training.account.api;

import com.github.rjansem.microservices.training.account.domain.pbi.account.LoanAccount;
import com.github.rjansem.microservices.training.account.service.LoanDetailsService;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.apisecurity.OwnRacine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rx.Single;

/**
 * Ressource REST gérant la synthèse des comptes credit
 *
 * @author aazzerrifi
 */
@Validated
@RestController
@RequestMapping(ApiConstants.CLIENTS_TECHNICAL_BY_ID)
public class LoanDetailsResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanDetailsResource.class);

    private final LoanDetailsService loanDetailsService;


    @Autowired
    public LoanDetailsResource(LoanDetailsService loanDetailsService) {
        this.loanDetailsService = loanDetailsService;
    }

    @RequestMapping(value = ApiConstants.LOAN_DETAILS, method = RequestMethod.GET)
    public Single<LoanAccount> findLoans(@PathVariable @OwnRacine String clientTechnicalId,
                                         @PathVariable String loanId,
                                         @AuthenticationPrincipal AuthenticatedUser user) {
        return loanDetailsService.findLoanDetailsByLoanId(loanId, clientTechnicalId, user)
                .doOnNext(loanAccount -> LOGGER.info("[ANALYTICS] [{}] [Account] [LoanDetails]", user.getLogin()))
                .toSingle();
    }

    @RequestMapping(value = ApiConstants.LOAN_DETAILS_BIS, method = RequestMethod.GET)
    public Single<LoanAccount> findLoans(@PathVariable @OwnRacine String clientTechnicalId,
                                         @PathVariable String loanId1,
                                         @PathVariable String loanId2,
                                         @AuthenticationPrincipal AuthenticatedUser user) {
        String loanId = loanId1 + "/" + loanId2;
        return loanDetailsService.findLoanDetailsByLoanId(loanId, clientTechnicalId, user).toSingle();
    }
}
