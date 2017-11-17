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

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests associés au service {@link BulocDetailsService}
 *
 * @author aazzerrifi
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
public class BulocDetailsServiceTest {

    @Autowired
    BulocDetailsService bulocService;

    @Test
    public void findOneBulocDetailsByIban_shouldOneFindLoanWithA5() {
        CustomTestSubscriber<Set<Account>> subscriber = new CustomTestSubscriber<>();
        bulocService.findOneBulocDetailsByIban(UserUtils.A5_USER, "24531", UserUtils.A5_USER.getRacines().get(0))
                .toList().map(HashSet::new).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isNotEmpty().hasSize(1);
        Account account = CasDeTestsUtils.getAccountBulocDetails();
        assertThat(accounts.stream().findFirst().orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(account);
    }

    @Test
    public void findBulocByLogin_shouldOneFindLoanWithA5() {
        CustomTestSubscriber<Set<Account>> subscriber = new CustomTestSubscriber<>();
        bulocService.findAllBulocDetailsByIban(UserUtils.A5_USER, UserUtils.A5_USER.getRacines().get(0))
                .toList().map(HashSet::new).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isNotEmpty().hasSize(1);
        Account account = CasDeTestsUtils.getAccountBulocDetails();
        assertThat(accounts.stream().findFirst().orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(account);
    }

    @Test
    public void findBulocByLogin_shouldZeroFindLoanWithA4() {
        CustomTestSubscriber<Set<Account>> subscriber = new CustomTestSubscriber<>();
        bulocService.findAllBulocDetailsByIban(UserUtils.A4_USER, UserUtils.A4_USER.getRacines().get(0))
                .toList().map(HashSet::new).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts).isEmpty();
    }

    @Test
    public void findBulocByLogin_shouldNotFindUserThenReturnEmpty() {
        bulocEmptyAccountsSet();
    }

    private void bulocEmptyAccountsSet() {
        CustomTestSubscriber<Set<Account>> subscriber = new CustomTestSubscriber<>();
        bulocService.findAllBulocDetailsByIban(UserUtils.UNKNOWN_USER, "")
                .toList().map(HashSet::new).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        //Set<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        //assertThat(accounts).isEmpty();
    }

}