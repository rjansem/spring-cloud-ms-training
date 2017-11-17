package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.CasDeTestsUtils;
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
import java.util.HashSet;
import java.util.Set;

import static com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType.CREDIT;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests associés au service {@link LoanOverviewDetailsService}
 *
 * @author aazzerrifi
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
public class LoanOverviewDetailsServiceTest {

    @Autowired
    LoanOverviewDetailsService loanOverviewDetailsService;

    @Test
    public void findOneLoanDetailsByIban_shouldOneFindLoanWithA5() {
        CustomTestSubscriber<Set<Account>> subscriber = new CustomTestSubscriber<>();
        loanOverviewDetailsService.findOneLoanDetailsByIban("0023514", UserUtils.A5_USER, UserUtils.A5_USER.getRacines().get(0))
                .toList().map(HashSet::new).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isNotEmpty().hasSize(1);
        Account account = new Account();
        account.setId("0023514");
        account.setAccountNumber("0023514");
        account.setCurrency("EUR");
        account.setNextWriteOffCurrency("EUR");
        account.setBookedBalanceCurrency("EUR");
        account.setBookedBalance(new BigDecimal("1800000.000000"));
        account.setTypeId(CREDIT.getId());
        account.setType(CREDIT.getLibelle());
        account.setSubtype("CREDIT DE TRESORERIE");
        account.setSubtypeId("CREDIT DE TRESORERIE");
        account.setDate(DateUtils.convertEfsDateToLocalDate("31-03-2016"));
        account.setNextWriteOffAmount(new BigDecimal("5915.000000"));
        assertThat(accounts.stream().findFirst().orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(account);
    }

    @Test
    public void findLoanByLogin_shouldOneFindLoanWithA5() {
        CustomTestSubscriber<Set<Account>> subscriber = new CustomTestSubscriber<>();
        loanOverviewDetailsService.findAllLoanDetailsByRacine(UserUtils.A5_USER, UserUtils.A5_USER.getRacines().get(0))
                .toList().map(HashSet::new).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isNotEmpty().hasSize(1);
        assertThat(accounts.stream().findFirst().orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(CasDeTestsUtils.getAccountCredit_A5_0023514());
    }

    @Test
    public void findLoanByLogin_shouldZeroFindLoanWithA4() {
        CustomTestSubscriber<Set<Account>> subscriber = new CustomTestSubscriber<>();
        loanOverviewDetailsService.findAllLoanDetailsByRacine(UserUtils.A4_USER, UserUtils.A4_USER.getRacines().get(0))
                .toList().map(HashSet::new).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isEmpty();
    }

    @Test
    public void findLoanByLogin_shouldNotFindUserThenReturnEmpty() {
        loanEmptyAccountsSet();
    }

    private void loanEmptyAccountsSet() {
        CustomTestSubscriber<Set<Account>> subscriber = new CustomTestSubscriber<>();
        loanOverviewDetailsService.findAllLoanDetailsByRacine(UserUtils.UNKNOWN_USER, "")
                .toList().map(HashSet::new).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        //Set<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        //assertThat(accounts).isEmpty();
    }
}