package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.CasDeTestsUtils;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.domain.pbi.account.AccountGroup;
import com.github.rjansem.microservices.training.account.domain.pbi.account.AccountGroupType;
import com.github.rjansem.microservices.training.commons.testing.CustomTestSubscriber;
import com.github.rjansem.microservices.training.commons.testing.UserUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests associés au service {@link CheckingAndSavingDetailsService}
 *
 * @author aazzerrifi
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
public class ChekingAndSavingDetailsServiceTest {

    @Autowired
    private CheckingAndSavingDetailsService checkingAndSavingDetailsService;

    @Test
    public void findAccountGroupByRacine() {
        CustomTestSubscriber<AccountGroup> subscriber = new CustomTestSubscriber<>();
        checkingAndSavingDetailsService.findAccountGroupByRacine(UserUtils.A5_USER, UserUtils.A5_USER.getRacines().get(0))
                .subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        AccountGroup group = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(group.getAccounts()).isNotEmpty().hasSize(3);
        AccountGroup accountGroup = new AccountGroup();
        accountGroup.setGroupName(AccountGroupType.CURRENT_SAVING.getLibelle());
        accountGroup.setGroupId(AccountGroupType.CURRENT_SAVING.getId());
        accountGroup.setCurrency("EUR");
        accountGroup.setTotalBalance(new BigDecimal("1831312.750000"));
        accountGroup.setTotalBalanceDate(group.getTotalBalanceDate());
        List<Account> accounts = new ArrayList<>();
        accounts.add(CasDeTestsUtils.getAccountDat());
        accounts.add(CasDeTestsUtils.getAccountEp_A5_USER());
        accounts.add(CasDeTestsUtils.getAccountCC_A5_USER());
        accountGroup.setAccounts(accounts);
        //assertThat(group.getTotalBalance()).isEqualToComparingFieldByField(accountGroup.getTotalBalance());
        assertThat(group.getTotalBalance().toString()).isEqualTo(accountGroup.getTotalBalance().toString());
    }

    @Test
    public void findAllAccountsForThisGroupByUser_A5_USER() {
        CustomTestSubscriber<List<Account>> subscriber = new CustomTestSubscriber<>();
        checkingAndSavingDetailsService.findAllAccountsForThisGroupByUser(UserUtils.A5_USER, UserUtils.A5_USER.getRacines().get(0))
                .subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        List<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isNotEmpty().hasSize(3);
        Account account = CasDeTestsUtils.getAccountCC_A5_USER();
        assertThat(accounts.stream().findFirst().orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(account);
    }

    @Test
    public void findAllChekingDetailsByIban_A5_USER() {
        CustomTestSubscriber<Set<Account>> subscriber = new CustomTestSubscriber<>();
        checkingAndSavingDetailsService.findAllChekingsDetailsByIban(UserUtils.A5_USER, UserUtils.A5_USER.getRacines().get(0))
                .toList().map(HashSet::new).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isNotEmpty().hasSize(1);
        Account accountTest = CasDeTestsUtils.getAccountCC_A5_USER();
        assertThat(accounts.stream().findFirst().orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(accountTest);
    }

    @Test
    public void findOneChekingDetailsByIban_A5_USER() {
        CustomTestSubscriber<Account> subscriber = new CustomTestSubscriber<>();
        checkingAndSavingDetailsService.findOneChekingDetailsByIban("FR7630788001002504780000351", UserUtils.A5_USER,
                UserUtils.A5_USER.getRacines().get(0)).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Account account = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(account.getId()).isNotNull();
        Account accountTest = CasDeTestsUtils.getAccountCC_A5_USER();
        assertThat(account).isEqualToComparingFieldByField(accountTest);
    }

    @Test
    public void findAllSavingsDetailsByIban_A5_USER() {
        CustomTestSubscriber<Set<Account>> subscriber = new CustomTestSubscriber<>();
        checkingAndSavingDetailsService.findAllSavingsDetailsByIban(UserUtils.A5_USER, UserUtils.A5_USER.getRacines().get(0))
                .toList().map(HashSet::new).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isNotEmpty().hasSize(1);
        Account accountTest = CasDeTestsUtils.getAccountEp_A5_USER();
        assertThat(accounts.stream().findFirst().orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(accountTest);
    }

    @Test
    public void findOneSavingDetailsByIban_A5_USER() {
        CustomTestSubscriber<Account> subscriber = new CustomTestSubscriber<>();
        checkingAndSavingDetailsService.findOneSavingDetailsByIban("FR7630788001002504780000353", UserUtils.A5_USER,
                UserUtils.A5_USER.getRacines().get(0)).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Account account = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(account.getId()).isNotNull();
        Account accountTest = CasDeTestsUtils.getAccountEp_A5_USER();
        assertThat(account).isEqualToComparingFieldByField(accountTest);
    }

    @Test
    public void findDetailsAllDatByIban_A5_USER() {
        CustomTestSubscriber<Set<Account>> subscriber = new CustomTestSubscriber<>();
        checkingAndSavingDetailsService.findDetailsAllDatByIban(UserUtils.A5_USER, UserUtils.A5_USER.getRacines().get(0))
                .toList().map(HashSet::new).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isNotEmpty().hasSize(1);
        Account accountTest = CasDeTestsUtils.getAccountDat();
        assertThat(accounts.stream().findFirst().orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(accountTest);
    }

    @Test
    public void findDetailsOneDatByNum_A5_USER() {
        CustomTestSubscriber<Account> subscriber = new CustomTestSubscriber<>();
        checkingAndSavingDetailsService.findDetailsOneDatByNum("35964", UserUtils.A5_USER,
                UserUtils.A5_USER.getRacines().get(0)).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Account account = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(account.getId()).isNotNull();
        Account accountTest = CasDeTestsUtils.getAccountDat();
        assertThat(account).isEqualToComparingFieldByField(accountTest);
    }

    @Test
    public void findAllAccountsForThisGroupByUser_A4R1() {
        CustomTestSubscriber<List<Account>> subscriber = new CustomTestSubscriber<>();
        checkingAndSavingDetailsService.findAllAccountsForThisGroupByUser(UserUtils.A4_USER, UserUtils.A4_USER.getRacines().get(0))
                .subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        List<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isNotEmpty().hasSize(3);
        Account account = CasDeTestsUtils.getAccountCC();
        assertThat(accounts.stream().filter(account1 -> account1.getId().equals("FR7630788001002504780000342")).findFirst().get())
                .isEqualToComparingFieldByField(account);
    }

    @Test
    public void findAllChekingDetailsByIban_A4R1() {
        CustomTestSubscriber<Set<Account>> subscriber = new CustomTestSubscriber<>();
        checkingAndSavingDetailsService.findAllChekingsDetailsByIban(UserUtils.A4_USER, UserUtils.A4_USER.getRacines().get(0))
                .toList().map(HashSet::new).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isNotEmpty().hasSize(2);
        Account accountTest = CasDeTestsUtils.getAccountCC();
        assertThat(accounts.stream().findFirst().orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(accountTest);
    }

    @Test
    public void findOneChekingDetailsByIban_A4R1() {
        CustomTestSubscriber<Account> subscriber = new CustomTestSubscriber<>();
        checkingAndSavingDetailsService.findOneChekingDetailsByIban("FR7630788001002504780000341", UserUtils.A4_USER,
                UserUtils.A4_USER.getRacines().get(0)).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Account account = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(account.getId()).isNotNull();
        Account accountTest = CasDeTestsUtils.getAccountCC_A4();
        assertThat(account).isEqualToComparingFieldByField(accountTest);
    }

    @Test
    public void findAllSavingsDetailsByIban_A4R1() {
        CustomTestSubscriber<Set<Account>> subscriber = new CustomTestSubscriber<>();
        checkingAndSavingDetailsService.findAllSavingsDetailsByIban(UserUtils.A4_USER, UserUtils.A4_USER.getRacines().get(0))
                .toList().map(HashSet::new).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isNotEmpty().hasSize(1);
        Account accountTest = CasDeTestsUtils.getAccountEp();
        assertThat(accounts.stream().findFirst().orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(accountTest);
    }

    @Test
    public void findOneSavingDetailsByIban_A4R1() {
        CustomTestSubscriber<Account> subscriber = new CustomTestSubscriber<>();
        checkingAndSavingDetailsService.findOneSavingDetailsByIban("FR7630788001002504780000343", UserUtils.A4_USER,
                UserUtils.A4_USER.getRacines().get(0)).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Account account = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(account.getId()).isNotNull();
        Account accountTest = CasDeTestsUtils.getAccountEp();
        assertThat(account).isEqualToComparingFieldByField(accountTest);
    }

    @Test
    public void findDetailsAllDatByIban_NULL_A4R1() {
        CustomTestSubscriber<Set<Account>> subscriber = new CustomTestSubscriber<>();
        checkingAndSavingDetailsService.findDetailsAllDatByIban(UserUtils.A4_USER, UserUtils.A4_USER.getRacines().get(0))
                .toList().map(HashSet::new).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isEmpty();
    }

    @Test
    public void findDetailsOneDatByNum_NULL_A4R1() {
        CustomTestSubscriber<Account> subscriber = new CustomTestSubscriber<>();
        assertThat(checkingAndSavingDetailsService.findDetailsOneDatByNum("0000", UserUtils.A4_USER,
                UserUtils.A4_USER.getRacines().get(0)).isEmpty());
    }
}