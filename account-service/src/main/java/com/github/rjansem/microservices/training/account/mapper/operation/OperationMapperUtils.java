package com.github.rjansem.microservices.training.account.mapper.operation;

import java.math.BigDecimal;

/**
 * Utilitaires de mapping d'opérations
 *
 * @author rjansem
 */
final class OperationMapperUtils {

    private static final String CREDIT_INDICATOR = "CRDT";

    private static final String DEBIT_INDICATOR = "DBIT";

    static String creditDebitIndicator(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) <= 0 ? DEBIT_INDICATOR : CREDIT_INDICATOR;
    }

}
