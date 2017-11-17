package com.github.rjansem.microservices.training.transaction.service;

import com.github.rjansem.microservices.training.commons.testing.CustomTestSubscriber;
import com.github.rjansem.microservices.training.commons.testing.UserUtils;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.CompteSubType;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.Ordre;
import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.Transaction;
import com.github.rjansem.microservices.training.transaction.mapper.commun.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests associés au service transaction account
 *
 * @author mbouhamyd
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Test
    public void findAllTransactions_shouldNotFindTRansaction() throws Exception {
        CustomTestSubscriber<List<Transaction>> subscriber = new CustomTestSubscriber<>();
        transactionService.findTransactions(UserUtils.A2_USER, null).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        List<Transaction> transactions = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(subscriber.getOnNextEvents().isEmpty());
        assertThat(transactions).isEmpty();
    }

    @Test
    public void findAllTransactions_shouldFindOneTRansaction() throws Exception {
        CustomTestSubscriber<List<Transaction>> subscriber = new CustomTestSubscriber<>();
        List<Ordre> ordres= new ArrayList<>();
        Ordre first= new Ordre("WBSCT3_O_58501245c5438a1c_0");
        ordres.add(first);
        transactionService.findTransactions(UserUtils.A1_USER, ordres).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        List<Transaction> transactions = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(subscriber.getOnNextEvents().isEmpty());
        assertThat(transactions).isNotEmpty().hasSize(1);
        Transaction transactionExcepted = new Transaction();
        transactionExcepted.setId("WBSCT3_O_58501245c5438a1c_0");
        transactionExcepted.setStatus("4096");
        transactionExcepted.setAccountId("FR7630788001008888888888830");
        transactionExcepted.setAccountNumber("88888888888");
        transactionExcepted.setType(CompteSubType.findLibelleById("COMPTE A VUE").getType());
        transactionExcepted.setTypeId(CompteSubType.findLibelleById("COMPTE A VUE").getTypeId());
        transactionExcepted.setSubtype("COMPTE A VUE");
        transactionExcepted.setSubtypeId(CompteSubType.findLibelleById("COMPTE A VUE").getId());
        transactionExcepted.setInitiationDate("2016-12-13T00:00:00Z");
        transactionExcepted.setBookingDateTime("2017-05-01T00:00:00Z");
        transactionExcepted.setCounterpartyIban("FR7630003001001111111111132");
        transactionExcepted.setCounterpartyId("FR7630003001001111111111132");
        transactionExcepted.setCounterpartyLastName("Benef");
        BigDecimal amount = new BigDecimal("100.000000");
        transactionExcepted.setInstructedAmount(amount);
        transactionExcepted.setInstructedCurrency("EUR");
        transactionExcepted.setCreditDebitIndicator("DEBIT");
        transactionExcepted.setPaymentMode("SINGLE");
        assertThat(transactions).contains(transactionExcepted);
        System.out.print(transactions);
    }

    @Test
    public void findAllTransactions_shouldFindTwoTRansaction() throws Exception {
        CustomTestSubscriber<List<Transaction>> subscriber = new CustomTestSubscriber<>();
        List<Ordre> ordres= new ArrayList<>();
        Ordre first= new Ordre("WBSCT3_O_58501245c5438a1c_1");
        Ordre seconde= new Ordre("WBSCT3_O_58501245c5438a1c_1");
        ordres.add(first);
        ordres.add(seconde);
        transactionService.findTransactions(UserUtils.A3_USER, ordres).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        List<Transaction> transactions = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(subscriber.getOnNextEvents().isEmpty());
        assertThat(transactions).isNotEmpty().hasSize(2);
        Transaction transactionExcepted = new Transaction();
        transactionExcepted.setId("WBSCT3_O_58501245c5438a1c_1");
        transactionExcepted.setStatus("4096");
        transactionExcepted.setAccountId("FR7630788001008888888888830");
        transactionExcepted.setAccountNumber("88888888889");
        transactionExcepted.setType(CompteSubType.findLibelleById("COMPTE A VUE").getType());
        transactionExcepted.setTypeId(CompteSubType.findLibelleById("COMPTE A VUE").getTypeId());
        transactionExcepted.setSubtype("COMPTE A VUE");
        transactionExcepted.setSubtypeId(CompteSubType.findLibelleById("COMPTE A VUE").getId());
        transactionExcepted.setInitiationDate("2016-12-13T00:00:00Z");
        transactionExcepted.setBookingDateTime("2017-05-01T00:00:00Z");
        transactionExcepted.setCounterpartyIban("FR7630003001001111111111132");
        transactionExcepted.setCounterpartyId("FR7630003001001111111111132");
        transactionExcepted.setCounterpartyLastName("Benef");
        BigDecimal amount = new BigDecimal("100.000000");
        transactionExcepted.setInstructedAmount(amount);
        transactionExcepted.setInstructedCurrency("EUR");
        transactionExcepted.setCreditDebitIndicator("DEBIT");
        transactionExcepted.setPaymentMode("SINGLE");

        Transaction secondetransactionExcepted = new Transaction();
        secondetransactionExcepted.setId("WBSCT3_O_58501245c5438a1c_1");
        secondetransactionExcepted.setStatus("4096");
        secondetransactionExcepted.setAccountId("FR7630788001008888888888830");
        secondetransactionExcepted.setAccountNumber("88888888889");
        secondetransactionExcepted.setType(CompteSubType.findLibelleById("COMPTE A VUE").getType());
        secondetransactionExcepted.setTypeId(CompteSubType.findLibelleById("COMPTE A VUE").getTypeId());
        secondetransactionExcepted.setSubtype("COMPTE A VUE");
        secondetransactionExcepted.setSubtypeId(CompteSubType.findLibelleById("COMPTE A VUE").getId());
        secondetransactionExcepted.setInitiationDate("2016-12-13T00:00:00Z");
        secondetransactionExcepted.setBookingDateTime("2017-05-01T00:00:00Z");
        secondetransactionExcepted.setCounterpartyIban("FR7630003001001111111111132");
        secondetransactionExcepted.setCounterpartyId("FR7630003001001111111111132");
        secondetransactionExcepted.setCounterpartyLastName("Benef");
        BigDecimal secondamount = new BigDecimal("100.000000");
        secondetransactionExcepted.setInstructedAmount(secondamount);
        secondetransactionExcepted.setInstructedCurrency("EUR");
        secondetransactionExcepted.setCreditDebitIndicator("DEBIT");
        secondetransactionExcepted.setPaymentMode("SINGLE");
        assertThat(transactions).contains(transactionExcepted, secondetransactionExcepted);
        System.out.print(transactions);
    }
}