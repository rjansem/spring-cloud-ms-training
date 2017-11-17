package com.github.rjansem.microservices.training.transaction.service;

import com.github.rjansem.microservices.training.commons.testing.CustomTestSubscriber;
import com.github.rjansem.microservices.training.commons.testing.UserUtils;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.MoyenSignature;
import com.github.rjansem.microservices.training.transaction.domain.pbi.PaymentOrder;
import com.github.rjansem.microservices.training.transaction.domain.pbi.TransactionInfo;
import com.github.rjansem.microservices.training.transaction.domain.pbi.account.TransactionAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests associés au service {@link MeansSignatureService}
 *
 * @author aazzerrifi
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
public class MeansSignatureServiceTest {

    @Autowired
    MeansSignatureService meansSignatureService;

    @Test
    public void checkMeansOfSignature_INT() {
        CustomTestSubscriber<MoyenSignature> subscriber = new CustomTestSubscriber<>();
        meansSignatureService.checkMeansOfSignature(UserUtils.A3_USER, getPaymentOrder_INT_200()).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        MoyenSignature moyenSignature = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(moyenSignature.getAlarmes()).isEmpty();
        MoyenSignature expected = new MoyenSignature();
        expected.setType("KBV");
        expected.setNiveauSecurite(0);
        expected.setAlarmes(new ArrayList<>());
        assertThat(moyenSignature).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void checkMeansOfSignature_INT_ALARME() {
        CustomTestSubscriber<MoyenSignature> subscriber = new CustomTestSubscriber<>();
        meansSignatureService.checkMeansOfSignature(UserUtils.A3_USER, getPaymentOrder_INT_2000000()).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
    }

    @Test
    public void checkMeansOfSignature_EXT_WITHOUT_VKB() {
        CustomTestSubscriber<MoyenSignature> subscriber = new CustomTestSubscriber<>();
        meansSignatureService.checkMeansOfSignature(UserUtils.A3_USER, getPaymentOrder_EXT()).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
    }

    @Test
    public void checkMeansOfSignature_INT_WITHOUT_VKB() {
        CustomTestSubscriber<MoyenSignature> subscriber = new CustomTestSubscriber<>();
        meansSignatureService.checkMeansOfSignature(UserUtils.A2_USER, getPaymentOrder_INT_200()).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
    }

    @Test
    public void checkMeansOfSignature_UNKNOWN_USER() {
        CustomTestSubscriber<MoyenSignature> subscriber = new CustomTestSubscriber<>();
        meansSignatureService.checkMeansOfSignature(UserUtils.UNKNOWN_USER, getPaymentOrder_INT_2000000()).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
    }

    @Test
    public void checkMeansOfSignature_EXT_UNKNOWN_USER() {
        CustomTestSubscriber<MoyenSignature> subscriber = new CustomTestSubscriber<>();
        meansSignatureService.checkMeansOfSignature(UserUtils.UNKNOWN_USER, getPaymentOrder_EXT()).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
    }

    private PaymentOrder getPaymentOrder_EXT() {
        PaymentOrder input = new PaymentOrder();
        TransactionAccount debitAccount = new TransactionAccount();
        debitAccount.setId("FR7630788001002504780000350");
        debitAccount.setIban("FR7630788001002504780000350");
        TransactionAccount creditAccount = new TransactionAccount();
        creditAccount.setId("101301");
        creditAccount.setIban("FR7630003001001111111111101");
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setInstructedAmount(new BigDecimal("200.00"));
        transactionInfo.setExecutionDateTime("2016-12-14T10:21:15.223Z");
        transactionInfo.setMemo("Merci mon ami");
        transactionInfo.setPaymentMode("SINGLE");
        transactionInfo.setUrgentTransfer(false);
        transactionInfo.setInstructedCurrency("EUR");
        input.setDebitAccount(debitAccount);
        input.setCreditAccount(creditAccount);
        input.setTransactionInfo(transactionInfo);
        return input;
    }

    private PaymentOrder getPaymentOrder_INT_200() {
        PaymentOrder input = new PaymentOrder();
        TransactionAccount debitAccount = new TransactionAccount();
        debitAccount.setId("FR7630788001002504780000350");
        debitAccount.setIban("FR7630788001002504780000350");
        TransactionAccount creditAccount = new TransactionAccount();
        creditAccount.setId("FR7630003001001111111111101");
        creditAccount.setIban("FR7630003001001111111111101");
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setInstructedAmount(new BigDecimal("200.00"));
        transactionInfo.setExecutionDateTime("2016-12-14T10:21:15.223Z");
        transactionInfo.setMemo("Merci mon ami");
        transactionInfo.setPaymentMode("SINGLE");
        transactionInfo.setUrgentTransfer(false);
        transactionInfo.setInstructedCurrency("EUR");
        input.setDebitAccount(debitAccount);
        input.setCreditAccount(creditAccount);
        input.setTransactionInfo(transactionInfo);
        return input;
    }

    private PaymentOrder getPaymentOrder_INT_2000000() {
        PaymentOrder input = new PaymentOrder();
        TransactionAccount debitAccount = new TransactionAccount();
        debitAccount.setId("FR7630788001002504780000350");
        debitAccount.setIban("FR7630788001002504780000350");
        TransactionAccount creditAccount = new TransactionAccount();
        creditAccount.setId("FR7630003001001111111111101");
        creditAccount.setIban("FR7630003001001111111111101");
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setInstructedAmount(new BigDecimal("2000000.00"));
        transactionInfo.setExecutionDateTime("2016-12-14T10:21:15.223Z");
        transactionInfo.setMemo("Merci mon ami");
        transactionInfo.setPaymentMode("SINGLE");
        transactionInfo.setUrgentTransfer(false);
        transactionInfo.setInstructedCurrency("EUR");
        input.setDebitAccount(debitAccount);
        input.setCreditAccount(creditAccount);
        input.setTransactionInfo(transactionInfo);
        return input;
    }

}