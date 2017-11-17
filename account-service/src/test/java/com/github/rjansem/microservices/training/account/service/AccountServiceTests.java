package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.domain.pbi.account.AccountOverview;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.apisecurity.Authority;
import com.github.rjansem.microservices.training.commons.testing.CustomTestSubscriber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests associés au service {@link AccountService}
 *
 * @author jntakpe
 * @author aazzerrifi
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
public class AccountServiceTests {

    private AuthenticatedUser user_A5 = new AuthenticatedUser("token", "A5",
            Stream.of("Racine").collect(Collectors.toList()), Stream.of(Authority.CONSULTATION).collect(Collectors.toList()));

    private AuthenticatedUser user = new AuthenticatedUser("token", "A4",
            Stream.of("Racine").collect(Collectors.toList()), Stream.of(Authority.CONSULTATION).collect(Collectors.toList()));

    private AuthenticatedUser userNotFound = new AuthenticatedUser("token", "9999",
            Stream.of("Racine").collect(Collectors.toList()), Stream.of(Authority.CONSULTATION).collect(Collectors.toList()));

    @Autowired
    private AccountService accountService;

    @Test
    public void findAllAccountsByLogin_shouldFindOneSavingAndOneChecking() {
        CustomTestSubscriber<List<Account>> subscriber = new CustomTestSubscriber<> ( );
        accountService.findAllAccountsByLogin(user, "A4R1").subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        List<Account> accounts = subscriber.assertNoErrorsThenWaitAndGetValue ( );
        assertThat(accounts).isNotEmpty().hasSize(5);
        assertThat(accounts.stream().filter(a -> a.getType().equals(CompteType.EPARGNE.getLibelle())).count()).isEqualTo(1);
        assertThat(accounts.stream().filter(a -> a.getType().equals(CompteType.COURANT.getLibelle())).count()).isEqualTo(2);
        assertThat(accounts.stream().filter(a -> a.getType().equals(CompteType.PORTEFEUILLE_TITRES.getLibelle())).count()).isEqualTo(1);
        assertThat(accounts.stream().filter(a -> a.getType().equals(CompteType.ASSURANCE_VIE.getLibelle())).count()).isEqualTo(1);
    }

    @Test
    public void findAccountOverviewByLogin_shouldFindOneSavingAndOneChecking() {
        CustomTestSubscriber<AccountOverview> subscriber = new CustomTestSubscriber<>();
        accountService.findAccountOverviewByLogin(user_A5, "A5R1").subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        AccountOverview accounts = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(accounts.getAccountGroups()).isNotEmpty().hasSize(4);
        assertThat(accounts.getAccountGroups().get(0).getAccounts()).isNotEmpty().hasSize(3);
        assertThat(accounts.getAccountGroups().get(1).getAccounts()).isNotEmpty().hasSize(2);
        assertThat(accounts.getAccountGroups().get(0).getAccounts().stream()
                .filter(a -> a.getType().equals(CompteType.EPARGNE.getLibelle())).count()).isEqualTo(1);
        assertThat(accounts.getAccountGroups().get(0).getAccounts().stream()
                .filter(a -> a.getType().equals(CompteType.COURANT.getLibelle())).count()).isEqualTo(1);
        assertThat(accounts.getAccountGroups().get(1).getAccounts().stream()
                .filter(a -> a.getType().equals(CompteType.PORTEFEUILLE_TITRES.getLibelle())).count()).isEqualTo(1);
        assertThat(accounts.getAccountGroups().get(1).getAccounts().stream()
                .filter(a -> a.getType().equals(CompteType.ASSURANCE_VIE.getLibelle())).count()).isEqualTo(1);
    }

}
