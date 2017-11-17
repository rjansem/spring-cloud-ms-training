package com.github.rjansem.microservices.training.account.mapper.operation;

import com.github.rjansem.microservices.training.account.domain.efs.operation.LibelleWrapper;
import com.github.rjansem.microservices.training.account.domain.efs.operation.OperationCompte;
import com.github.rjansem.microservices.training.account.domain.pbi.operation.Operation;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests associés au service operation
 *
 * @author mbouhamyd
 */
public class OperationCompteToOperationMapperTest {

    private static final String LINE_SEP = System.getProperty("line.separator");

    @Test
    public void map() throws Exception {
        OperationCompte operationCompte = new OperationCompte();
        operationCompte.setDevise(10);
        operationCompte.setMontantDevise("20.000000");
        operationCompte.setMontantEuro("20.000000");
        operationCompte.setDateOperation("31-10-2016");
        operationCompte.setDateValeur("31-10-2016");
        operationCompte.setLibelle("RACHAT 1");
        List<LibelleWrapper> complements = Stream.of(new LibelleWrapper().setLibelle("l1"),
                new LibelleWrapper().setLibelle("l2"),
                new LibelleWrapper().setLibelle("l3")).collect(Collectors.toList());
        operationCompte.setComplements(complements);
        Operation operationExpected = new Operation();
        operationExpected.setAccountId("FR7630788001002504780000311");
        operationExpected.setBookingDateTime("2016-10-31T00:00:00.000Z");
        operationExpected.setOperationDateTime("2016-10-31T00:00:00.000Z");
        operationExpected.setTransactionAmount(new BigDecimal("20.000000"));
        operationExpected.setTransactionCurrency("10");
        operationExpected.setCreditDebitIndicator("CRDT");
        operationExpected.setInformationFirst("RACHAT 1");
        operationExpected.setInformationSecond("l1" + LINE_SEP + "l2" + LINE_SEP + "l3");
        assertThat(new OperationCompteToOperationMapper().map(operationCompte, "FR7630788001002504780000311")).isEqualToComparingFieldByField(operationExpected);
    }

}