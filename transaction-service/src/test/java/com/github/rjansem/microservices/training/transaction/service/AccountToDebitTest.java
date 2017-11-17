package com.github.rjansem.microservices.training.transaction.service;

import com.github.rjansem.microservices.training.commons.domain.GenericContent;
import com.github.rjansem.microservices.training.commons.testing.CustomTestSubscriber;
import com.github.rjansem.microservices.training.commons.testing.UserUtils;
import com.github.rjansem.microservices.training.transaction.domain.pbi.account.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests associés au service accountToDebit
 *
 * @author mbouhamyd
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
public class AccountToDebitTest {

    @Autowired
    private AccountToDebitService accountToDebitService;

    @Test
    public void findaccounttodebit_shouldNotFindAccount() throws Exception {
        CustomTestSubscriber<GenericContent<Account>> subscriber = new CustomTestSubscriber<>();
        accountToDebitService.retrieveAccountToDebit(UserUtils.A1_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        GenericContent<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts.getContent()).isEmpty();
    }

    @Test
    public void findaccounttodebit_UNKNOWN_USER() throws Exception {
        CustomTestSubscriber<GenericContent<Account>> subscriber = new CustomTestSubscriber<>();
        accountToDebitService.retrieveAccountToDebit(UserUtils.UNKNOWN_USER)
                .doOnError(throwable -> {
                    assertEquals("Recherche les comptes emetteur du UNKNOWN", throwable.getMessage());
                    fail(throwable.getMessage());
                }).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        assertThat(subscriber.getOnErrorEvents().isEmpty());
    }

    @Test
    public void findaccounttodebit_shouldFindOneAccount() throws Exception {
        CustomTestSubscriber<GenericContent<Account>> subscriber = new CustomTestSubscriber<>();
        accountToDebitService.retrieveAccountToDebit(UserUtils.A2_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        GenericContent<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts.getContent()).isNotEmpty().hasSize(1);
        assertThat(accounts.getContent().stream().findFirst().get().getType().toString()).isEqualTo("Compte courant");
        assertThat(accounts.getContent().stream().findFirst().get().getTypeId().toString()).isEqualTo("DAV");
        assertThat(accounts.getContent().stream().findFirst().get().getSubtype().toString()).isEqualTo("COMPTE A VUE");
        assertThat(accounts.getContent().stream().findFirst().get().getSubtypeId().toString()).isEqualTo("CAV");
        assertThat(accounts.getContent().stream().findFirst().get().getId().toString()).isEqualTo("FR7630788001001010689671817");
        assertEquals(accounts.getContent().stream().findFirst().get().getAccountNumber(), "10106896718");
        assertThat(accounts.getContent().stream().findFirst().get().getAccountLabel().toString()).isEqualTo("CPTE SPECIAL");
        BigDecimal balance = new BigDecimal("10000.000000");
        assertEquals(accounts.getContent().stream().findFirst().get().getBalance(), balance);
        assertThat(accounts.getContent().stream().findFirst().get().getCurrency().toString()).isEqualTo("EUR");
        System.out.print(accounts.getContent());
    }

    @Test
    public void findaccounttodebit_shouldFindTwoAccount() throws Exception {
        CustomTestSubscriber<GenericContent<Account>> subscriber = new CustomTestSubscriber<>();
        accountToDebitService.retrieveAccountToDebit(UserUtils.A3_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        GenericContent<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts.getContent()).isNotEmpty().hasSize(2);
        Account firstAccountToDebit = new Account ();
        firstAccountToDebit.setType("Compte courant");
        firstAccountToDebit.setTypeId("DAV");
        firstAccountToDebit.setSubtype("COMPTE A VUE");
        firstAccountToDebit.setSubtypeId("CAV");
        firstAccountToDebit.setId("FR7630788001001010689671817");
        firstAccountToDebit.setAccountNumber("10106896718");
        firstAccountToDebit.setAccountLabel("CPTE SPECIAL");
        BigDecimal balance = new BigDecimal("10000.000000");
        firstAccountToDebit.setBalance(balance);
        firstAccountToDebit.setCurrency("EUR");
        Account secondAccountToDebit = new Account ();
        secondAccountToDebit.setType("Compte courant");
        secondAccountToDebit.setTypeId("DAV");
        secondAccountToDebit.setSubtype("COMPTE NANTI GERE");
        secondAccountToDebit.setSubtypeId("CNG");
        secondAccountToDebit.setId("FR7630788001001010689671999");
        secondAccountToDebit.setAccountNumber("10106896719");
        secondAccountToDebit.setAccountLabel("CPTE SPECIAL");
        BigDecimal secondbalance = new BigDecimal("20000.000000");
        secondAccountToDebit.setBalance(secondbalance);
        secondAccountToDebit.setCurrency("EUR");
        assertThat(accounts.getContent()).containsExactlyInAnyOrder(secondAccountToDebit, firstAccountToDebit);
        System.out.print(accounts.getContent());
    }

}