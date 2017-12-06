package com.github.rjansem.microservices.training.account.api;

import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.service.AccountService;
import com.github.rjansem.microservices.training.account.util.AccountTypeValidator;
import com.github.rjansem.microservices.training.apisecurity.OwnRacine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rx.Single;

import java.util.List;

/**
 * Ressource REST gérant la synthèse des comptes
 *
 * @author rjansem
 */
@Validated
@RestController
@RequestMapping(ApiConstants.CLIENTS_TECHNICAL_BY_ID)
public class AccountResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountResource.class);

    private final AccountService accountService;

    private AccountTypeValidator accountTypeValidator;

    @Autowired
    public AccountResource(AccountService accountService,
                           AccountTypeValidator accountTypeValidator) {
        this.accountService = accountService;
        this.accountTypeValidator = accountTypeValidator;
    }

    @RequestMapping(value = ApiConstants.ACCOUNT, method = RequestMethod.GET)
    public Single<List<Account>> findAllAccountsByLogin(@PathVariable String userId,
                                                        @PathVariable String clientTechnicalId) {
        return accountService.findAllAccountsByLogin(userId, clientTechnicalId)
                .doOnNext(cs -> LOGGER.debug("Récupération de {} comptes pour le client {}", cs.size(), clientTechnicalId))
                .toSingle();
    }


}
