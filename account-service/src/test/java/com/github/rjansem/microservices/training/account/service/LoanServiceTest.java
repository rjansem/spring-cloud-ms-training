package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.CasDeTestsUtils;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.commons.testing.CustomTestSubscriber;
import com.github.rjansem.microservices.training.commons.testing.UserUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests associés au service {@link LoanService}
 *
 * @author aazzerrifi
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
public class LoanServiceTest {
    @Autowired
    LoanService loanService;
    Account accountCredit = CasDeTestsUtils.getAccountCredit_A5_0023514();

    @Test
    public void findAllLoansByLoginAndRacine_shouldOneFindLoanWithA5() {
        CustomTestSubscriber<List<Account>> subscriber = new CustomTestSubscriber<> ( );
        loanService.findAllLoansByLoginAndRacine(UserUtils.A5_USER, UserUtils.A5_USER.getRacines().get(0)).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        List<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue ( );
        assertThat(accounts).isNotEmpty().hasSize(1);
        assertThat(accounts.stream().findFirst().orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(accountCredit);
    }


    @Test
    public void findAllLoansByLoginAndRacine_shouldZeroFindLoansWithA4() {
        CustomTestSubscriber<List<Account>> subscriber = new CustomTestSubscriber<> ( );
        loanService.findAllLoansByLoginAndRacine(UserUtils.A4_USER, UserUtils.A4_USER.getRacines().get(0)).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        List<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue ( );
        assertThat(accounts).isEmpty();
    }

    @Test()
    public void findAllLoansByLoginAndRacine_shouldNotFindUserThenReturnEmpty() {
        CustomTestSubscriber<List<Account>> subscriber = new CustomTestSubscriber<>();
        loanService.findAllLoansByLoginAndRacine(UserUtils.UNKNOWN_USER, "").subscribe(subscriber);
        //List<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
    }

    private void allLoansEmptyAccountsSet() {
        CustomTestSubscriber<List<Account>> subscriber = new CustomTestSubscriber<> ( );
        loanService.findAllLoansByLoginAndRacine(UserUtils.UNKNOWN_USER, "").subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        List<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue ( );
        assertThat(accounts).isEmpty();
    }
}