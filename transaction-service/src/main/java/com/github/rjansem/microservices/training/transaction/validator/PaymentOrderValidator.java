package com.github.rjansem.microservices.training.transaction.validator;

import com.github.rjansem.microservices.training.transaction.domain.pbi.PaymentOrder;
import com.github.rjansem.microservices.training.transaction.domain.pbi.TransactionInfo;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Validator de l'objet {@link PaymentOrder}
 * @author rjansem
 */
@Component
public class PaymentOrderValidator {

    /**
     * Validation d'un paymentOrder
     * @param order
     */
    public void validate(PaymentOrder order) {
        List<String> errors = new ArrayList<>();

        if (order != null) {
            TransactionInfo transactionInfo = order.getTransactionInfo();
            if (order.getDebitAccount().getId() == null) {
                errors.add("id ne peut pas être nul");
            }
            if (transactionInfo != null && !"SINGLE".equals(transactionInfo.getPaymentMode())) {
                errors.add("paymentMode attribute must be equal to SINGLE");
            }
            if (transactionInfo.getExecutionDateTime() == null) {
                errors.add("executionDateTime ne peut pas être nul");
            }
            if (transactionInfo.getInstructedAmount() == null) {
                errors.add("instructedAmount ne peut pas être nul");
            }
            if (transactionInfo.getInstructedCurrency() == null) {
                errors.add("instructedCurrency ne peut pas être nul");
            }
        }

        if (!CollectionUtils.isEmpty(errors)) {
            throw new ValidationException(errors.get(0));
        }
    }
}
