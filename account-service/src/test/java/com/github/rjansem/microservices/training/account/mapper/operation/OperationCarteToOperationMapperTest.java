package com.github.rjansem.microservices.training.account.mapper.operation;

import com.github.rjansem.microservices.training.account.domain.efs.cartebancaire.CarteBancaire;
import com.github.rjansem.microservices.training.account.domain.efs.operation.OperationCarte;
import com.github.rjansem.microservices.training.account.domain.pbi.operation.Operation;
import com.github.rjansem.microservices.training.commons.domain.utils.DateUtils;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Mapper transformant des {@link CarteBancaire} et {@link OperationCarte} en {@link Operation}
 *
 * @author aazzerrifi
 */
public class OperationCarteToOperationMapperTest {
    @Test
    public void map() throws Exception {

        CarteBancaire creditCard = new CarteBancaire(
                111,
                "VISA CLASSIC internationale",
                "1002504780000351",
                "M DOE CHRISTIAN",
                "-14.000000",
                10,
                "ACT",
                "28-04-2017",
                "FR7630788001002504780000351",
                "NADAL CHRISTIAN",
                "01-12-2016");

        OperationCarte operationCarte = new OperationCarte();
        operationCarte.setMontant("1000");
        operationCarte.setCodeDevise("EUR");
        operationCarte.setDateAchat("11-12-2016");
        operationCarte.setNomCommercant("Toto");
        operationCarte.setLieuAchat("Paris");

        Operation operation = new Operation();
        String opeCarteMontant = operationCarte.getMontant();
        BigDecimal montant = BigDecimal.ZERO;
        if (opeCarteMontant != null) {
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

        assertThat(new OperationCarteToOperationMapper().map(creditCard, operationCarte)).isEqualToComparingFieldByField(operation);
    }

}