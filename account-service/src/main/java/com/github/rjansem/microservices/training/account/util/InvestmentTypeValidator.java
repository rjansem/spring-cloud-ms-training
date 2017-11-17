package com.github.rjansem.microservices.training.account.util;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Validator d'investmentType
 *
 * @author mbouhamyd
 */
@Component
public class InvestmentTypeValidator {
    static final String PORTEFEUILLES = "PortefeuilleTitres";

    static final String ASSURANCEVIE = "AssuranceVie";

    /**
     * Validation d'investmentType
     *
     * @param investmentType
     */
    public void validate(String investmentType) {
        List<String> errors = new ArrayList<>();
        if ((!investmentType.equals(PORTEFEUILLES) && !investmentType.equals(ASSURANCEVIE) || investmentType == null)) {
            String message = String.format("InvestmentType non valide :%s", investmentType);
            errors.add(message);
        }
        if (!CollectionUtils.isEmpty(errors)) {
            throw new ValidationException(errors.get(0));
        }
    }
}
