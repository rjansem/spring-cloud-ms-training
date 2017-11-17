package com.github.rjansem.microservices.training.transaction.api;

import com.github.rjansem.microservices.training.transaction.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.transaction.service.AccountToDebitService;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.apisecurity.ServicesUris;
import com.github.rjansem.microservices.training.commons.domain.GenericContent;
import com.github.rjansem.microservices.training.transaction.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.transaction.service.AccountToDebitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rx.Single;

/**
 * @author mbouhamyd
 */
@Validated
@RestController
@RequestMapping(value = ServicesUris.API + ApiConstants.ACCOUNT_TO_DEBIT)
public class AccountToDebitResource {

    private final AccountToDebitService accountToDebitService;

    @Autowired
    public AccountToDebitResource(AccountToDebitService accountToDebitService) {
        this.accountToDebitService = accountToDebitService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Single<GenericContent<Account>> findAccountToDebit(@AuthenticationPrincipal AuthenticatedUser user) {
        return accountToDebitService.retrieveAccountToDebit(user).toSingle();
    }
}
