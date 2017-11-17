package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.commons.domain.utils.DateUtils;
import com.github.rjansem.microservices.training.commons.testing.CustomTestSubscriber;
import com.github.rjansem.microservices.training.commons.testing.UserUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests associés au service {@link ChekingAndSavingService}
 *
 * @author jntakpe
 * @author aazzerrifi
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
public class ChekingAndSavingServiceTests {

    @Autowired
    private ChekingAndSavingService chekingAndSavingService;


    @Test
    public void findCheckingsAndSavingsAccountsByLoginAndRacine_shouldFindAllAccountA4R1() {
        CustomTestSubscriber<List<Account>> subscriber = new CustomTestSubscriber<>();
        chekingAndSavingService.findAllAccountsByRacine(UserUtils.A4_USER, UserUtils.A4_USER.getRacines().get(0))
                .subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        List<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isNotEmpty().hasSize(3);
        Account account = new Account();
        account.setAccountNumber("00000000002");
        account.setId("FR7630788001002504780000342");
        account.setSubtype("COMPTE A VUE");
        account.setTypeId(COURANT.getId());
        account.setType(COURANT.getLibelle());
        account.setSubtypeId("CAV");
        account.setCurrency("10");
        account.setBookedBalance(new BigDecimal("12132.75"));
        account.setCreditcardsTotalBalance(new BigDecimal("1234"));
        account.setIban("FR7630788001002504780000342");
        account.setCreditcardsTotalDate("2016-11-09T00:00:00.000Z");
        assertThat(accounts.stream().filter(acc -> acc.getAccountNumber().equals("00000000002")).findFirst().orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(account);
    }

    @Test
    public void findCheckingsAndSavingsAccountsByLoginAndRacine_shouldFindAllAccountA5R1() {
        CustomTestSubscriber<List<Account>> subscriber = new CustomTestSubscriber<>();
        chekingAndSavingService.findAllAccountsByRacine(UserUtils.A5_USER, UserUtils.A5_USER.getRacines().get(0))
                .subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        List<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isNotEmpty().hasSize(3);
        Account account = new Account();
        account.setAccountNumber("00000000001");
        account.setId("FR7630788001002504780000351");
        account.setSubtype("COMPTE A VUE");
        account.setTypeId(COURANT.getId());
        account.setType(COURANT.getLibelle());
        account.setSubtypeId("CAV");
        account.setCurrency("10");
        account.setIban("FR7630788001002504780000351");
        account.setCreditcardsTotalDate("2016-11-09T00:00:00.000Z");
        account.setBookedBalance(new BigDecimal("2312.75"));
        account.setCreditcardsTotalBalance(new BigDecimal("-1000.000000"));
        assertThat(accounts.stream().filter(acc -> acc.getAccountNumber().equals("00000000001")).findFirst().orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(account);
    }

    @Test
    public void findCheckingsAccountsByLogin_shouldFindTwoAccount() {
        CustomTestSubscriber<List<Account>> subscriber = new CustomTestSubscriber<>();
        chekingAndSavingService.findCheckingsAccountsByClientLogin(UserUtils.A4_USER, UserUtils.A4_USER.getRacines().get(0))
                .toList().subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        List<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isNotEmpty().hasSize(2);
        Account account = new Account();
        account.setAccountNumber("00000000002");
        account.setId("FR7630788001002504780000342");
        account.setSubtype("COMPTE A VUE");
        account.setTypeId(COURANT.getId());
        account.setType(COURANT.getLibelle());
        account.setSubtypeId("CAV");
        account.setCurrency("10");
        account.setBookedBalance(new BigDecimal("12132.75"));
        account.setCreditcardsTotalBalance(new BigDecimal("1234"));
        account.setIban("FR7630788001002504780000342");
        account.setCreditcardsTotalDate("2016-11-09T00:00:00.000Z");
        assertThat(accounts.stream().findFirst().orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(account);
        //TODO tester l'autre compte
    }

    @Test
    public void findCheckingsAccountsByLogin_shouldNotFindUserThenReturnEmpty() {
        checkEmptyAccountsSet();
    }

    @Test
    public void findSavingsAccountsByLogin_shouldFindOneAccount() {
        CustomTestSubscriber<List<Account>> subscriber = new CustomTestSubscriber<>();
        chekingAndSavingService.findSavingsAccountsByLogin(UserUtils.A4_USER, UserUtils.A4_USER.getRacines().get(0))
                .toList().subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        List<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isNotEmpty().hasSize(1);
        Account account = new Account();
        account.setAccountNumber("20158070005");
        account.setId("FR7630788001002504780000343");
        account.setSubtype("PEA");
        account.setTypeId(EPARGNE.getId());
        account.setType(EPARGNE.getLibelle());
        account.setSubtypeId("PEA");
        account.setCurrency("10");
        account.setIban("FR7630788001002504780000343");
        account.setBookedBalance(new BigDecimal("900.77"));
        assertThat(accounts.stream().findFirst().orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(account);
    }

    @Test
    public void findSavingsAccountsByLogin_shouldNotFindUserThenReturnEmpty() {
        savEmptyAccountsSet();
    }

    @Test
    public void findDepotsATermeAccountsByClientLogin_shouldFindZeroAccount() {
        CustomTestSubscriber<List<Account>> subscriber = new CustomTestSubscriber<>();
        chekingAndSavingService.findDepotsATermeAccountsByClientLogin(UserUtils.A4_USER, UserUtils.A4_USER.getRacines().get(0))
                .toList().subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        List<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isEmpty();
    }

    @Test
    public void findDepotsATermeAccountsByClientLogin_shouldFindOneAccount() {
        CustomTestSubscriber<List<Account>> subscriber = new CustomTestSubscriber<>();
        chekingAndSavingService.findDepotsATermeAccountsByClientLogin(UserUtils.A5_USER, UserUtils.A5_USER.getRacines().get(0))
                .toList().subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        List<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isNotEmpty().hasSize(1);
        Account account = new Account();
        account.setAccountNumber("25138710002");
        account.setId("35964");
        account.setTypeId(DEPOT_A_TERME.getId());
        account.setType(DEPOT_A_TERME.getLibelle());
        account.setBookedBalance(new BigDecimal("1800000.000000"));
        account.setBookedBalanceDate("2015-11-10T00:00:00.000Z");
        account.setCurrency("10");
        account.setDate(DateUtils.convertEfsDateToLocalDate("20-11-2015"));
        assertThat(accounts.stream().findFirst().orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(account);
    }

    @Test
    public void findDepotsATermeAccountsByClientLogin_shouldNotFindUserThenReturnEmpty() {
        datEmptyAccountsSet();
    }

    private void checkEmptyAccountsSet() {
        CustomTestSubscriber<List<Account>> subscriber = new CustomTestSubscriber<>();
        chekingAndSavingService.findCheckingsAccountsByClientLogin(UserUtils.UNKNOWN_USER, "").toList().subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        // List<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        // assertThat(accounts).isEmpty();
    }

    private void savEmptyAccountsSet() {
        CustomTestSubscriber<List<Account>> subscriber = new CustomTestSubscriber<>();
        chekingAndSavingService.findSavingsAccountsByLogin(UserUtils.UNKNOWN_USER, "").toList().subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        // List<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        // assertThat(accounts).isEmpty();
    }

    private void datEmptyAccountsSet() {
        CustomTestSubscriber<List<Account>> subscriber = new CustomTestSubscriber<>();
        chekingAndSavingService.findDepotsATermeAccountsByClientLogin(UserUtils.UNKNOWN_USER, "")
                .toList().subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        //List<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        //assertThat(accounts).isEmpty();
    }
}
