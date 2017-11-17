package com.github.rjansem.microservices.training.account.util;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Validator d'accountType
 *
 * @author mbouhamyd
 */
@Component
public class AccountTypeValidator {
    static final String COURANT = "DAV";

    static final String EPARGNE = "Epargne";

    /**
     * Validation d'accountType
     *
     * @param accountType
     */
    public void validate(String accountType) {
        List<String> errors = new ArrayList<>();
        if ((!accountType.equals(COURANT) && !accountType.equals(EPARGNE) || accountType == null)) {
            String message = String.format("accountType non valide :%s", accountType);
            errors.add(message);
        }
        if (!CollectionUtils.isEmpty(errors)) {
            throw new ValidationException(errors.get(0));
        }
    }
}
