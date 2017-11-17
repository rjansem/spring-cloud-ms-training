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
 * Tests associés au service {@link InvestmentDetailsService}
 *
 * @author aazzerrifi
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
public class InvestmentDetailsServiceTest {

    Account accountPF = CasDeTestsUtils.getAccountPF_80000351();
    Account accountLI = CasDeTestsUtils.getAccountLI_80000001();
    @Autowired
    private InvestmentDetailsService investmentDetailsService;

    @Test
    public void findAccountGroupByRacine() {
        CustomTestSubscriber<AccountGroup> subscriber = new CustomTestSubscriber<>();
        investmentDetailsService.findAccountGroupByRacine(UserUtils.A5_USER, UserUtils.A5_USER.getRacines().get(0))
                .subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        AccountGroup group = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(group.getAccounts()).isNotEmpty().hasSize(2);
        AccountGroup accountGroup = new AccountGroup();
        accountGroup.setGroupName(AccountGroupType.INVESTMENTS.getLibelle());
        accountGroup.setGroupId(AccountGroupType.INVESTMENTS.getId());
        accountGroup.setCurrency("EUR");
        accountGroup.setTotalBalance(new BigDecimal("7334604.73"));
        accountGroup.setTotalBalanceDate(group.getTotalBalanceDate());
        List<Account> accounts = new ArrayList<>();
        accounts.add(accountPF);
        accounts.add(accountLI);
        accountGroup.setAccounts(accounts);
        assertThat(group).isEqualToComparingFieldByField(accountGroup);
    }

    @Test
    public void findAllAccountsForThisGroupByUser_A5R1() {
        CustomTestSubscriber<List<Account>> subscriber = new CustomTestSubscriber<>();
        investmentDetailsService.findAllAccountsForThisGroupByUser(UserUtils.A5_USER, UserUtils.A5_USER.getRacines().get(0))
                .subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        List<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isNotEmpty().hasSize(2);
        Account account = accountPF;
        assertThat(accounts.stream().findFirst().orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(account);
    }

    @Test
    public void findAllLifeInsuranceExtDetailsByIban_A5R1() {
        CustomTestSubscriber<Set<Account>> subscriber = new CustomTestSubscriber<>();
        investmentDetailsService.findAllLifeInsuranceExtDetailsByIban(UserUtils.A5_USER, UserUtils.A5_USER.getRacines().get(0))
                .toList().map(HashSet::new).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isNotEmpty().hasSize(1);
        Account accountTest = accountLI;
        assertThat(accounts.stream().findFirst().orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(accountTest);
    }

    @Test
    public void findAllLifeInsuranceNobcDetailsByIban_A5R1_NULL() {
        CustomTestSubscriber<Set<Account>> subscriber = new CustomTestSubscriber<>();
        investmentDetailsService.findAllLifeInsuranceNobcDetailsByIban(UserUtils.A5_USER, UserUtils.A5_USER.getRacines().get(0))
                .toList().map(HashSet::new).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isEmpty();
    }

    @Test
    public void findOneLifeInsuranceDetailsByIban_A5R1() {
        CustomTestSubscriber<Account> subscriber = new CustomTestSubscriber<>();
        investmentDetailsService.findLifeInsuranceById("80000001", UserUtils.A5_USER,
                UserUtils.A5_USER.getRacines().get(0)).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Account account = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(account.getId()).isNotNull();
        Account accountTest = accountLI;
        assertThat(account).isEqualToComparingFieldByField(accountTest);
    }

    @Test
    public void findAllPortfoliosDetailsByIban_A5R1() {
        CustomTestSubscriber<Set<Account>> subscriber = new CustomTestSubscriber<>();
        investmentDetailsService.findAllPortfolioDetailsByIban(UserUtils.A5_USER, UserUtils.A5_USER.getRacines().get(0))
                .toList().map(HashSet::new).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isNotEmpty().hasSize(1);
        Account accountTest = accountPF;
        assertThat(accounts.stream().findFirst().orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(accountTest);
    }

    @Test
    public void findOnePortfolioDetailsByIban_A5R1() {
        CustomTestSubscriber<Account> subscriber = new CustomTestSubscriber<>();
        investmentDetailsService.findOnePortfolioDetailsByNumero("80000351", UserUtils.A5_USER,
                UserUtils.A5_USER.getRacines().get(0)).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Account account = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(account.getId()).isNotNull();
        Account accountTest = accountPF;
        assertThat(account).isEqualToComparingFieldByField(accountTest);
    }

    @Test
    public void findOnePortfolioDetailsByIban_A4R1_NULL() {
        assertThat(investmentDetailsService.findOnePortfolioDetailsByNumero("80000341", UserUtils.A4_USER,
                UserUtils.A4_USER.getRacines().get(0)).isEmpty());
    }

    @Test
    public void findLifeInsuranceById_A4R1_NULL() {
        assertThat(investmentDetailsService.findLifeInsuranceById("800003410", UserUtils.A4_USER,
                UserUtils.A4_USER.getRacines().get(0)).isEmpty());
    }

    @Test
    public void findOneLifeInsuranceExtDetailsByIban_A4R1_NULL() {
        assertThat(investmentDetailsService.findOneLifeInsuranceExtDetailsByIban("800003410", UserUtils.A4_USER,
                UserUtils.A4_USER.getRacines().get(0)).isEmpty());
    }

    @Test
    public void findOneLifeInsuranceNobcDetailsByIban_A4R1_NULL() {
        assertThat(investmentDetailsService.findOneLifeInsuranceNobcDetailsByIban("", UserUtils.UNKNOWN_USER, "")
                .isEmpty());
    }

    @Test
    public void findAllPortfolioDetailsByIban_NULL() {
        CustomTestSubscriber<Set<Account>> subscriber = new CustomTestSubscriber<>();
        investmentDetailsService.findAllPortfolioDetailsByIban(UserUtils.UNKNOWN_USER, "")
                .toList().map(HashSet::new).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        //Set<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue().;
        //assertThat(accounts).isEmpty();
    }

    @Test
    public void findAllLifeInsuranceExtDetailsByIban_NULL() {
        CustomTestSubscriber<Set<Account>> subscriber = new CustomTestSubscriber<>();
        investmentDetailsService.findAllLifeInsuranceExtDetailsByIban(UserUtils.UNKNOWN_USER, "")
                .toList().map(HashSet::new).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        // Set<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        //assertThat(accounts).isEmpty();
    }

    @Test
    public void findAllLifeInsuranceNobcDetailsByIban_NULL() {
        CustomTestSubscriber<Set<Account>> subscriber = new CustomTestSubscriber<>();
        investmentDetailsService.findAllLifeInsuranceNobcDetailsByIban(UserUtils.UNKNOWN_USER, "")
                .toList().map(HashSet::new).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
    }

    @Test
    public void findAllLifeInsuranceByRacine_NULL() {
        CustomTestSubscriber<List<Account>> subscriber = new CustomTestSubscriber<>();
        investmentDetailsService.findAllLifeInsuranceByRacine(UserUtils.UNKNOWN_USER, "")
                .toList().subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        //List<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        //assertThat(accounts).isEmpty();
    }
}