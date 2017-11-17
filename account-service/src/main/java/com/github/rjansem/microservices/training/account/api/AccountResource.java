package com.github.rjansem.microservices.training.account.api;

import com.github.rjansem.microservices.training.account.util.AccountTypeValidator;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.domain.pbi.account.AccountOverview;
import com.github.rjansem.microservices.training.account.domain.pbi.account.RetrieveBalance;
import com.github.rjansem.microservices.training.account.domain.pbi.account.RibWrapper;
import com.github.rjansem.microservices.training.account.service.AccountService;
import com.github.rjansem.microservices.training.account.service.CartesBancairesService;
import com.github.rjansem.microservices.training.account.service.RetrieveBalanceService;
import com.github.rjansem.microservices.training.account.service.RibService;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.apisecurity.OwnRacine;
import com.github.rjansem.microservices.training.commons.domain.GenericContent;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rx.Single;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Ressource REST gérant la synthèse des comptes
 *
 * @author jntakpe
 * @author aazzerrifi
 */
@Validated
@RestController
@RequestMapping(ApiConstants.CLIENTS_TECHNICAL_BY_ID)
public class AccountResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountResource.class);

    private static final String ALL_CREDIT_CARD_ID_PARAM = "all";

    private final AccountService accountService;

    private final RibService ribService;

    private final RetrieveBalanceService retrieveBalanceService;

    private final CartesBancairesService cartesBancairesService;

    private AccountTypeValidator accountTypeValidator;

    @Autowired
    public AccountResource(AccountService accountService, RibService ribService, RetrieveBalanceService retrieveBalanceService,
                           CartesBancairesService cartesBancairesService, AccountTypeValidator accountTypeValidator) {
        this.accountService = accountService;
        this.ribService = ribService;
        this.retrieveBalanceService = retrieveBalanceService;
        this.cartesBancairesService = cartesBancairesService;
        this.accountTypeValidator = accountTypeValidator;
    }

    @RequestMapping(value = ApiConstants.ACCOUNT_OVERVIEW, method = RequestMethod.GET)
    public Single<AccountOverview> findOverview(@PathVariable @OwnRacine String clientTechnicalId,
                                                @AuthenticationPrincipal AuthenticatedUser user) {
        return accountService.findAccountOverviewByLogin(user, clientTechnicalId)
                .doOnNext(a -> LOGGER.info("[ANALYTICS] [{}] [Account] [Overview]", user.getLogin()))
                .toSingle();
    }

    @RequestMapping(value = ApiConstants.ACCOUNT, method = RequestMethod.GET)
    public Single<List<Account>> findAllAccountsByLogin(@PathVariable @OwnRacine String clientTechnicalId,
                                                        @AuthenticationPrincipal AuthenticatedUser user) {
        return accountService.findAllAccountsByLogin(user, clientTechnicalId)
                .doOnNext(cs -> LOGGER.debug("Récupération de {} comptes pour le client {}", cs.size(), clientTechnicalId))
                .toSingle();
    }

    @RequestMapping(value = ApiConstants.RIB, method = RequestMethod.GET)
    public Single<RibWrapper> findRibs(@PathVariable @OwnRacine String clientTechnicalId, @AuthenticationPrincipal AuthenticatedUser user) {
        return ribService.findRibsByLoginAndRacine(user, clientTechnicalId).map(RibWrapper::new)
                .doOnNext(ribWrapper -> LOGGER.info("[ANALYTICS] [{}] [Account] [Rib]",
                        user.getLogin()
                ))
                .toSingle();
    }

    @RequestMapping(value = ApiConstants.RETRIEVE_BALANCE_ALL, method = RequestMethod.GET, headers = "Accept=application/json")
    public Single<GenericContent<RetrieveBalance>> findRetrieveBalanceForAllAccounts(@PathVariable @OwnRacine String clientTechnicalId,
                                                                                     @AuthenticationPrincipal AuthenticatedUser user) {

        return retrieveBalanceService.findRetrieveBalanceContent(user, clientTechnicalId).toSingle();
    }

    @RequestMapping(value = ApiConstants.RETRIEVE_BALANCE_ACCOUNT, method = RequestMethod.GET)
    public Single<RetrieveBalance> findRetrieveBalanceForOneAccount(@PathVariable @OwnRacine String clientTechnicalId,
                                                                    @AuthenticationPrincipal AuthenticatedUser user,
                                                                    @PathVariable String accountId,
                                                                    @RequestParam @NotNull String accountType,
                                                                    @RequestParam(required = false) String creditcardId) {
        accountTypeValidator.validate(accountType);
        if (StringUtils.isEmpty(creditcardId)) {
            return retrieveBalanceService.findOneRetrieveBalanceByIdAccount(accountId, accountType, user, clientTechnicalId).toSingle();
        }
        if (ALL_CREDIT_CARD_ID_PARAM.equalsIgnoreCase(creditcardId)) {
            return cartesBancairesService.findRetrieveBalanceByIban(accountId, user, clientTechnicalId).toSingle();
        }
        return cartesBancairesService.findRetrieveBalanceById(accountId, creditcardId, clientTechnicalId, user).toSingle();
    }
}
