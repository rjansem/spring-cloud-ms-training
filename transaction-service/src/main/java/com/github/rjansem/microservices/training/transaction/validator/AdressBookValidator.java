package com.github.rjansem.microservices.training.transaction.validator;

import com.github.rjansem.microservices.training.transaction.domain.pbi.AddressBook;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Validator de l'objet {@link AddressBook}
 *
 * @author rjansem
 */
@Component
public class AdressBookValidator {

    /**
     * Validation d'un transferType
     *
     * @param transferType
     */
    public void validate(String transferType) {
        List<String> errors = new ArrayList<>();
        if (!"SEPA".equals(transferType)) {
            errors.add("transferType attribute must be equal to SEPA");
        }
        if (!CollectionUtils.isEmpty(errors)) {
            throw new ValidationException(errors.get(0));
        }
    }
}
