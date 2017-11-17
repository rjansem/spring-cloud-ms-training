package com.github.rjansem.microservices.training.transaction.service;

import com.github.rjansem.microservices.training.commons.testing.CustomTestSubscriber;
import com.github.rjansem.microservices.training.commons.testing.UserUtils;
import com.github.rjansem.microservices.training.transaction.domain.pbi.PaymentOrder;
import com.github.rjansem.microservices.training.transaction.domain.pbi.TransactionInfo;
import com.github.rjansem.microservices.training.transaction.domain.pbi.account.TransactionAccount;
import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.ListOfTransaction;
import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.Payment;
import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.SignTransaction;
import org.junit.Ignore;
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
 * Tests associés au service {@link OrderService}
 *
 * @author aazzerrifi
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
public class OrderServiceTest{

    @Autowired
    private OrderService orderService;

    @Test
    public void findAllPaymentOrder_UNKNOWN_USER(){
        CustomTestSubscriber<List<PaymentOrder>> subscriber = new CustomTestSubscriber<>();
        orderService.initiateSEPAPaymentOrder(getPaymentOrder_INPUT(), UserUtils.UNKNOWN_USER).toList().subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        assertThat(subscriber.getOnErrorEvents()).isNotNull();
    }

    @Test
    public void findAllPaymentOrder_A3_Emession_Ordre(){
        CustomTestSubscriber<List<PaymentOrder>> subscriber = new CustomTestSubscriber<>();
        orderService.initiateSEPAPaymentOrder(getPaymentOrder_INPUT(), UserUtils.A3_USER).toList()
                .subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        List<PaymentOrder> listOfPaymentOrder = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(listOfPaymentOrder).isNotEmpty().hasSize(1);
        PaymentOrder paymentOrder = getPaymentOrder_OUTPUT();

        assertThat(listOfPaymentOrder.stream().findFirst().get().getId()).isEqualTo(paymentOrder.getId());
        assertThat(listOfPaymentOrder.stream().findFirst().get().getStatus())
                .isEqualTo(paymentOrder.getStatus());
        assertThat(listOfPaymentOrder.stream().findFirst().get().getCreditAccount().getId())
                .isEqualTo(paymentOrder.getCreditAccount().getId());
        assertThat(listOfPaymentOrder.stream().findFirst().get().getDebitAccount())
                .isEqualToComparingFieldByField(paymentOrder.getDebitAccount());
        assertThat(listOfPaymentOrder.stream().findFirst().get().getTransactionInfo().getInstructedAmount())
                .isEqualToComparingFieldByField(paymentOrder.getTransactionInfo().getInstructedAmount());
    }

    @Test
    public void signPayment_A3_One_Payment(){
        CustomTestSubscriber<ListOfTransaction> subscriber = new CustomTestSubscriber<>();
        orderService.signPayment(getSignTransaction(1), UserUtils.A3_USER)
                .subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        ListOfTransaction listOfTransaction = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(listOfTransaction.getTransactions()).isNotEmpty().hasSize(1);
        Payment payment = getPayment_OUTPUT();
        assertThat(listOfTransaction.getTransactions().stream().findFirst().get().getTransactionId())
                .isEqualTo(payment.getTransactionId());
    }

    @Test
    @Ignore
    public void signPayment_A3_2_Payment(){
        CustomTestSubscriber<ListOfTransaction> subscriber = new CustomTestSubscriber<>();
        orderService.signPayment(getSignTransaction(2), UserUtils.A3_USER)
                .subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        ListOfTransaction listOfTransaction = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(listOfTransaction.getTransactions()).isNotEmpty().hasSize(2);
        Payment payment = getPayment_OUTPUT();
        assertThat(listOfTransaction.getTransactions().stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(payment);
    }

    @Test
    @Ignore
    public void signPayment_A3_10_Payment(){
        CustomTestSubscriber<ListOfTransaction> subscriber = new CustomTestSubscriber<>();
        orderService.signPayment(getSignTransaction(10), UserUtils.A3_USER)
                .subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        ListOfTransaction listOfTransaction = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(listOfTransaction.getTransactions()).isNotEmpty().hasSize(10);
        Payment payment = getPayment_OUTPUT();
        assertThat(listOfTransaction.getTransactions().stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(payment);
    }

    @Test
    public void signPayment_UNKNOWN_USER_Payment(){
        CustomTestSubscriber<ListOfTransaction> subscriber = new CustomTestSubscriber<>();
        orderService.signPayment(getSignTransaction(1), UserUtils.UNKNOWN_USER)
                .subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        assertThat(subscriber.getOnErrorEvents()).isNotNull();
    }


    @Test
    public void findAllPaymentOrder_A3_CODE_APP_ERR(){
        CustomTestSubscriber<List<PaymentOrder>> subscriber = new CustomTestSubscriber<>();
        orderService.initiateSEPAPaymentOrder(getPaymentOrder_INPUT(), UserUtils.A3_USER).toList().subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        assertThat(subscriber.getOnErrorEvents()).isNotNull();
    }

    private PaymentOrder getPaymentOrder_INPUT(){
        PaymentOrder input = new PaymentOrder();
        TransactionAccount debitAccount = new TransactionAccount();
        debitAccount.setId("FR7630788001002504780000351");
        debitAccount.setIban("FR7630788001002504780000351");
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

    private PaymentOrder getPaymentOrder_OUTPUT(){
        PaymentOrder input = new PaymentOrder();
        TransactionAccount debitAccount = new TransactionAccount();
        debitAccount.setId("FR7630788001008888888888830");
        debitAccount.setIban("FR7630788001008888888888830");
        debitAccount.setType("Compte courant");
        debitAccount.setTypeId("DAV");
        debitAccount.setSubtype("COMPTE A VUE");
        debitAccount.setSubtypeId("CAV");
        debitAccount.setAccountBalance(new BigDecimal("23444334"));
        debitAccount.setAccountBalanceCurrency("EUR");
        debitAccount.setAccountNumber("88888888888");
        debitAccount.setAccountLabel("Compte A");
        TransactionAccount creditAccount = new TransactionAccount();
        creditAccount.setId("FR7630003001001111111111132");
        creditAccount.setIban("FR7630003001001111111111132");
        creditAccount.setBeneficiaryLastName("Benef 1");
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setInstructedAmount(new BigDecimal("100.000000"));
        transactionInfo.setExecutionDateTime("01-05-2017");
        transactionInfo.setMemo("Loyer");
        transactionInfo.setPaymentMode("SINGLE");
        transactionInfo.setUrgentTransfer(null);
        transactionInfo.setInstructedCurrency("EUR");
        input.setId("WBSCT3_O_58501245c5438a1c_0");
        input.setStatus("4096");
        input.setDebitAccount(debitAccount);
        input.setCreditAccount(creditAccount);
        input.setTransactionInfo(transactionInfo);
        return input;
    }

    private SignTransaction getSignTransaction(int nbreTrans){
        SignTransaction signTransaction = new SignTransaction();
        signTransaction.setEncryptedPassword("XXXXXXX");
        signTransaction.setKeyboardIdentifier("YYYYYYYY");
        String id = "WBSCT3_O_58501245c5438a1c_0";
        List<String> strings = new ArrayList<>();
        for(int i = 0; i < nbreTrans; i++) strings.add(id);
        signTransaction.setTransactionIds(strings);
        return signTransaction;
    }

    private Payment getPayment_OUTPUT(){
        Payment payment = new Payment("WBSCT3_O_58501245c5438a1c_0",
                "4096", "01-05-2017");
        return payment;
    }
}