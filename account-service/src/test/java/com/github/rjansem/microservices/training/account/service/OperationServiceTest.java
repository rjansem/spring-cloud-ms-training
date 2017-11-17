package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.domain.pbi.operation.Operation;
import com.github.rjansem.microservices.training.commons.testing.CustomTestSubscriber;
import com.github.rjansem.microservices.training.commons.testing.UserUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * Tests associés au service operation
 *
 * @author mbouhamyd
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
public class OperationServiceTest {

    @Autowired
    private OperationService operationService;

    @Test(expected = IllegalArgumentException.class)
    public void AccountOperationByIban_shouldNotFindOperation() {
        CustomTestSubscriber<Set<Operation>> subscriber = new CustomTestSubscriber<>();
        operationService.accountOperationByIban("FR7630788001002504780000311", "XXX", UserUtils.A1_USER.getRacines().get(0), UserUtils.A1_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        fail("AccountType invalid");
    }

    @Test
    public void AccountOperationByIbanA1_shouldNotFindOperation() {
        CustomTestSubscriber<Set<Operation>> subscriber = new CustomTestSubscriber<>();
        operationService.accountOperationByIban("FR7630788001002504780000311", "DAV", UserUtils.A1_USER.getRacines().get(0), UserUtils.A1_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Operation> opts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(opts).isEmpty();
        System.out.println(opts);

    }

    @Test
    public void AccountOperationByIbanA2_shouldFindOneOperation() {
        CustomTestSubscriber<Set<Operation>> subscriber = new CustomTestSubscriber<>();
        operationService.accountOperationByIban("FR7630788001002504780000311", "DAV", UserUtils.A2_USER.getRacines().get(0), UserUtils.A2_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Operation> opts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(opts).isNotEmpty().hasSize(1);
        Set<Operation> operationsExcpected = new HashSet<>();
        Operation operationExcpected = new Operation();
        operationExcpected.setAccountId("FR7630788001002504780000311");
        operationExcpected.setBookingDateTime("2016-10-31T00:00:00.000Z");
        operationExcpected.setOperationDateTime("2016-10-31T00:00:00.000Z");
        operationExcpected.setTransactionAmount(new BigDecimal("20.000000"));
        operationExcpected.setTransactionCurrency("EUR");
        operationExcpected.setCreditDebitIndicator("CRDT");
        operationExcpected.setInformationFirst("RACHAT 1");
        operationExcpected.setInformationSecond("ACHAT" + System.getProperty("line.separator") + "PSG");
        operationsExcpected.add(operationExcpected);
        assertThat(opts.stream().findFirst().orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(operationExcpected);
        System.out.println(opts);

    }

    @Test
    @Ignore
    public void AccountOperationByIbanA3_shouldFindTreeOperation() {
        CustomTestSubscriber<Set<Operation>> subscriber = new CustomTestSubscriber<>();
        operationService.accountOperationByIban("FR7630788001002504780000311", "DAV", UserUtils.A3_USER.getRacines().get(0), UserUtils.A3_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Operation> opts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(opts).isNotEmpty().hasSize(3);
        Set<Operation> operationsExcpected = new HashSet<>();
        Operation operationExcpected = new Operation();
        operationExcpected.setAccountId("FR7630788001002504780000311");
        operationExcpected.setBookingDateTime("2014-11-04T00:00:00Z");
        operationExcpected.setOperationDateTime("2014-11-04T00:00:00Z");
        operationExcpected.setTransactionAmount(new BigDecimal("1802.000000"));
        operationExcpected.setTransactionCurrency("EUR");
        operationExcpected.setCreditDebitIndicator("DBIT");
        operationExcpected.setInformationFirst("PRELEV  ");
        operationExcpected.setInformationSecond("Abonnement\nSFR\n");
        operationsExcpected.add(operationExcpected);
        assertThat(opts.containsAll(operationsExcpected));
        System.out.println(opts);

    }

    @Test
    public void AccountOperationByidCardA1_shouldNotFindOperation() {
        CustomTestSubscriber<Set<Operation>> subscriber = new CustomTestSubscriber<>();
        operationService.accountOperationByIdCard("111", UserUtils.A1_USER.getRacines().get(0), UserUtils.A1_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Operation> opts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(opts).isEmpty();
        System.out.println(opts);
    }

    @Test
    @Ignore
    public void AccountOperationByidCardA2_shouldFindOneOperation() {
        CustomTestSubscriber<Set<Operation>> subscriber = new CustomTestSubscriber<>();
        operationService.accountOperationByIdCard("113", UserUtils.A2_USER.getRacines().get(0), UserUtils.A2_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Operation> opts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(opts).isNotEmpty().hasSize(1);
        Set<Operation> operationsExcpected = new HashSet<>();
        Operation operationExcpected = new Operation();
        operationExcpected.setAccountId("FR7630788001002504780000351");
        operationExcpected.setCreditcardId("111");
        operationExcpected.setCreditcardAccountNumber("100250478XXXXXX1");
        operationExcpected.setBookingDateTime("2016-11-27T00:00:00.000Z");
        operationExcpected.setOperationDateTime("2016-11-27T00:00:00.000Z");
        operationExcpected.setTransactionAmount(new BigDecimal("-14.800000"));
        operationExcpected.setTransactionCurrency("EUR");
        operationExcpected.setMerchant("LE PARTHENON");
        operationExcpected.setCity("PARIS 8");
        operationExcpected.setCreditDebitIndicator("DBIT");
        operationsExcpected.add(operationExcpected);
        assertThat(opts.stream().findFirst().orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(operationExcpected);
        System.out.println(opts);
    }

    @Test
    public void AccountOperationByidCardA3_shouldFindTreeOperations() {
        CustomTestSubscriber<Set<Operation>> subscriber = new CustomTestSubscriber<>();
        operationService.accountOperationByIdCard("111", UserUtils.A3_USER.getRacines().get(0), UserUtils.A3_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Operation> opts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(opts).isEmpty();

    }

    @Test
    @Ignore
    public void AccountOperationForAllCreditCardA2_shouldFindTreeOperations() {
        CustomTestSubscriber<Set<Operation>> subscriber = new CustomTestSubscriber<>();
        operationService.accountOperationForAllCreditCardsByIban("FR7630788001002504780000311",
                UserUtils.A2_USER.getRacines().get(0),
                UserUtils.A2_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Operation> opts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(opts).isNotEmpty().hasSize(2);

    }

    @Test
    @Ignore
    public void AccountOperationForAllCreditCardA3_shouldFindTreeOperations() {
        CustomTestSubscriber<Set<Operation>> subscriber = new CustomTestSubscriber<>();
        operationService.accountOperationForAllCreditCardsByIban("FR7630788001002504780000311", UserUtils.A3_USER.getRacines().get(0), UserUtils.A3_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Operation> opts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(opts).isNotEmpty().hasSize(6);
        System.out.println(opts);
    }

    @Test
    public void AccountOperationForAllCreditCard_shouldNotFindOperations() {
        CustomTestSubscriber<Set<Operation>> subscriber = new CustomTestSubscriber<>();
        operationService.accountOperationForAllCreditCardsByIban("FR7630788001002504780000377", UserUtils.A3_USER.getRacines().get(0), UserUtils.A3_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
    }

}