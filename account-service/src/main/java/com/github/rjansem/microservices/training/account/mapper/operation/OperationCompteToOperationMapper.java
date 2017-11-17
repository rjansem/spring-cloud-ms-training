package com.github.rjansem.microservices.training.account.mapper.operation;

import com.github.rjansem.microservices.training.account.domain.efs.operation.LibelleWrapper;
import com.github.rjansem.microservices.training.account.domain.efs.operation.OperationCompte;
import com.github.rjansem.microservices.training.account.domain.pbi.operation.Operation;
import com.github.rjansem.microservices.training.commons.domain.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Mapper transformant une {@link OperationCompte Bancaire} en {@link Operation}
 *
 * @author mbouhamyd
 */
public class OperationCompteToOperationMapper {

    public Operation map(OperationCompte input, String iban) {
        Objects.requireNonNull(input);
        Operation operation = new Operation();
        if(StringUtils.isNotBlank(input.getMontantDevise())){
            BigDecimal transactionAmount = new BigDecimal(input.getMontantDevise());
            operation.setTransactionAmount(transactionAmount);
            operation.setCreditDebitIndicator(OperationMapperUtils.creditDebitIndicator(transactionAmount));
        }
        operation.setAccountId(iban);
        operation.setBookingDateTime(DateUtils.convertEfsDateToPibDate(input.getDateValeur()));
        operation.setOperationDateTime(DateUtils.convertEfsDateToPibDate(input.getDateOperation()));
        operation.setTransactionCurrency(String.valueOf(input.getDevise()));
        operation.setInformationFirst(input.getLibelle());
        operation.setInformationSecond(formatInfoSeconds(input.getComplements()));
        return operation;
    }

    private String formatInfoSeconds(List<LibelleWrapper> complements) {
        return complements.stream()
                .map(LibelleWrapper::getLibelle)
                .filter(Objects::nonNull)
                .map(String::trim)
                .collect(Collectors.joining(System.getProperty("line.separator")));
    }

}
