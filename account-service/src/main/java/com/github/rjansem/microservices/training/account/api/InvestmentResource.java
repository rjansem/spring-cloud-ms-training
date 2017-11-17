package com.github.rjansem.microservices.training.account.api;

import com.github.rjansem.microservices.training.account.util.InvestmentTypeValidator;
import com.github.rjansem.microservices.training.account.domain.pbi.portfolioAndLI.InvestissementDetail;
import com.github.rjansem.microservices.training.account.domain.pbi.position.PositionDetail;
import com.github.rjansem.microservices.training.account.service.PortfolioAndLIService;
import com.github.rjansem.microservices.training.account.service.PositionDetailService;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.apisecurity.OwnRacine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import rx.Single;

import javax.validation.constraints.NotNull;

/**
 * Resource pour la gestion des inverstments
 *
 * @author mbouhamyd
 */
@RestController
@RequestMapping(ApiConstants.CLIENTS_TECHNICAL_BY_ID)
public class InvestmentResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvestmentResource.class);

    private PositionDetailService positionDetailService;

    private PortfolioAndLIService portfolioAndLIService;

    private InvestmentTypeValidator investmentTypeValidator;

    @Autowired
    public InvestmentResource(PositionDetailService positionDetailService, PortfolioAndLIService portfolioAndLIService, InvestmentTypeValidator investmentTypeValidator) {
        this.positionDetailService = positionDetailService;
        this.portfolioAndLIService = portfolioAndLIService;
        this.investmentTypeValidator = investmentTypeValidator;
    }

    @RequestMapping(value = ApiConstants.POSITION_DETAIL, method = RequestMethod.GET)
    public Single<PositionDetail> findPositionDetail(@PathVariable @OwnRacine String clientTechnicalId,
                                                     @AuthenticationPrincipal AuthenticatedUser user,
                                                     @PathVariable String investmentId,
                                                     @PathVariable String positionId,
                                                     @RequestParam @NotNull String investmentType) {
        investmentTypeValidator.validate(investmentType);
        return positionDetailService.findPositionDetail(clientTechnicalId, investmentId, positionId, investmentType, user)
                .doOnNext(loanAccount -> LOGGER.info("[ANALYTICS] [{}] [Account] [PositionsDetails]", user.getLogin()))
                .toSingle();
    }

    @RequestMapping(value = ApiConstants.PORTFOLIO_LI, method = RequestMethod.GET)
    public Single<InvestissementDetail> findPortfolioLI(@PathVariable @OwnRacine String clientTechnicalId,
                                                        @AuthenticationPrincipal AuthenticatedUser user,
                                                        @PathVariable String investmentId,
                                                        @RequestParam @NotNull String investmentType) {
        investmentTypeValidator.validate(investmentType);
        return portfolioAndLIService.findPortfolioOrLifeInsurance(clientTechnicalId, investmentId, investmentType, user)
                .doOnNext(loanAccount -> LOGGER.info("[ANALYTICS] [{}] [Account] [PortfolioLifeInsurance]", user.getLogin()))
                .toSingle();
    }
}
