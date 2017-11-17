package com.github.rjansem.microservices.training.transaction.api;

import com.github.rjansem.microservices.training.transaction.domain.pbi.AddressBook;
import com.github.rjansem.microservices.training.transaction.service.RetrieveABookService;
import com.github.rjansem.microservices.training.transaction.validator.AdressBookValidator;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.apisecurity.ServicesUris;
import com.github.rjansem.microservices.training.commons.domain.GenericContent;
import com.github.rjansem.microservices.training.transaction.domain.pbi.AddressBook;
import com.github.rjansem.microservices.training.transaction.service.RetrieveABookService;
import com.github.rjansem.microservices.training.transaction.validator.AdressBookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rx.Single;

import javax.validation.constraints.NotNull;

/**
 * Ressource REST gérant les beneficaires
 *
 * @author aazzerrifi
 */
@Validated
@RestController
@RequestMapping(value = ServicesUris.API + ApiConstants.BENEFICIARIES)
public class BeneficiariesResource {

    private final RetrieveABookService retrieveABookService;

    private final AdressBookValidator adressBookValidator;

    @Autowired
    public BeneficiariesResource(RetrieveABookService retrieveABookService, AdressBookValidator adressBookValidator) {
        this.retrieveABookService = retrieveABookService;
        this.adressBookValidator = adressBookValidator;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Single<GenericContent<AddressBook>> findBeneficairesWithType(@AuthenticationPrincipal AuthenticatedUser user,
                                                                        @RequestParam @NotNull String transferType) {
        adressBookValidator.validate(transferType);
        return retrieveABookService.findAllBeneficiaires(user).toSingle();
    }
}
