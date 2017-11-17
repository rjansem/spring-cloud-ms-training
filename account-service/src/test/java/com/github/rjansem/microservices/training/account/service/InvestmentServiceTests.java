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
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests associés au service {@link InvestmentService}
 *
 * @author aazzerrifi
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
public class InvestmentServiceTests {

    Account accountPF = CasDeTestsUtils.getAccountPF_80000341();
    Account accountLI = CasDeTestsUtils.getAccountLI_800003410();
    @Autowired
    private InvestmentService investmentService;

    //TODO ajouter des cas de tests
    @Test
    public void findLifeInsuranceByLogin_shouldFindOneAccount() {
        CustomTestSubscriber<Set<Account>> subscriber = new CustomTestSubscriber<>();
        investmentService.findLifeInsuranceByLogin(UserUtils.A4_USER, UserUtils.A4_USER.getRacines().get(0)).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isNotEmpty().hasSize(1);
        Account account = accountLI;
        assertThat(accounts.stream().findFirst().orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(account);
    }

    public void findLifeInsuranceByLogin_shouldNotFindUserThenReturnEmpty() {
        checkEmptyAccountsList();
    }

    @Test
    public void findPorteFolioByLogin_shouldFindOneAccount() {
        CustomTestSubscriber<Set<Account>> subscriber = new CustomTestSubscriber<>();
        investmentService.findPortfolioByClientLogin(UserUtils.A4_USER, UserUtils.A4_USER.getRacines().get(0)).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isNotEmpty().hasSize(1);
        Account account = accountPF;
        assertThat(accounts.stream().findFirst().orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(account);
    }

    @Test()
    public void findPorteFolioByLogin_shouldNotFindUserThenReturnEmpty() {
        checkEmptyAccountsList();
    }

    private void checkEmptyAccountsList() {
        CustomTestSubscriber<List<Account>> subscriber = new CustomTestSubscriber<> ( );
        investmentService.findAllInvestmentsByLoginAndRacine(UserUtils.UNKNOWN_USER, "").subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        //List<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue ( );
        //assertThat(accounts).isEmpty();
    }

}
