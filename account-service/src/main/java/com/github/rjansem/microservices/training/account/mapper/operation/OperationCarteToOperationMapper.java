package com.github.rjansem.microservices.training.account.mapper.operation;

import com.github.rjansem.microservices.training.account.domain.efs.cartebancaire.CarteBancaire;
import com.github.rjansem.microservices.training.account.domain.efs.operation.OperationCarte;
import com.github.rjansem.microservices.training.account.domain.pbi.operation.Operation;
import com.github.rjansem.microservices.training.commons.domain.utils.DateUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * Mapper transformant une {@link OperationCarte} bancaire en {@link Operation}
 *
 * @author mbouhamyd
 */
public class OperationCarteToOperationMapper {

    public Operation map(CarteBancaire creditCard, OperationCarte operationCarte) {
        Operation operation = new Operation();
        String opeCarteMontant = operationCarte.getMontant();
        BigDecimal montant = BigDecimal.ZERO;
        if (StringUtils.isNotBlank(opeCarteMontant)) {
            montant = new BigDecimal(opeCarteMontant);
        }
        operation.setAccountId(creditCard.getIbanCompte());
        operation.setCreditcardId(String.valueOf(creditCard.getId()));
        if (!creditCard.getNumero().isEmpty() && creditCard.getNumero().length() > 14) {
            String start = creditCard.getNumero().substring(0, 9);
            String end = creditCard.getNumero().substring(15);
            String numero = start + "XXXXXX" + end;
            operation.setCreditcardAccountNumber(numero);
        }
        operation.setTransactionCurrency(operationCarte.getCodeDevise());
        if (!operationCarte.getCodeDevise().equals(creditCard.getLibelleDevise())) {
            operation.setInstructedCurrency(creditCard.getLibelleDevise());
            operation.setInstructedAmount(montant);
        }
        operation.setBookingDateTime(DateUtils.convertEfsDateToPibDate(operationCarte.getDateAchat()));
        operation.setOperationDateTime(DateUtils.convertEfsDateToPibDate(operationCarte.getDateAchat()));
        operation.setMerchant(operationCarte.getNomCommercant());
        operation.setTransactionAmount(montant);
        operation.setCreditDebitIndicator(OperationMapperUtils.creditDebitIndicator(montant));
        operation.setCity(operationCarte.getLieuAchat());
        return operation;
    }
}
