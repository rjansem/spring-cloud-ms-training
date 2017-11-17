package com.github.rjansem.microservices.training.profile.service;

import com.github.rjansem.microservices.training.profile.domain.pib.Account;
import com.github.rjansem.microservices.training.profile.domain.pib.Client;
import com.github.rjansem.microservices.training.profile.domain.pib.ClientList;
import com.github.rjansem.microservices.training.commons.testing.CustomTestSubscriber;
import com.github.rjansem.microservices.training.commons.testing.UserUtils;
import com.github.rjansem.microservices.training.exception.ExceptionCode;
import com.github.rjansem.microservices.training.exception.NOBCException;
import com.github.rjansem.microservices.training.profile.domain.pib.Account;
import com.github.rjansem.microservices.training.profile.domain.pib.Client;
import com.github.rjansem.microservices.training.profile.domain.pib.ClientList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests associés au service {@link ClientService}
 *
 * @author jntakpe
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
public class ClientServiceTests {

    @Autowired
    private ClientService clientService;

    @Test
    public void findClientsWithAccountsByLogin_shouldReturnEmptySetCuzUnknownLogin() {
        CustomTestSubscriber<Object> subscriber = new CustomTestSubscriber<>();
        clientService.findClientsWithAccountsByLogin(UserUtils.UNKNOWN_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Throwable clients = subscriber.getOnErrorEvents().get(0);
        String msg = String.format("Impossible de récupérer les racines de l'utilisateur %s", UserUtils.UNKNOWN_USER.getLogin());
        NOBCException nobcException = new NOBCException(clients, new ExceptionCode() {
            @Override
            public int getStatus() {
                return 400;
            }
        });
        System.out.println("test: " + clients.getMessage());
        subscriber.assertError(nobcException);
        //    Throwable clients = (Throwable) subscriber.assertNoErrorsThenWaitAndGetValue ( );


    }

    @Test
    public void findClientsWithAccountsByLogin_shouldReturnEmptySetCuzNoRacines() {
        CustomTestSubscriber<ClientList> subscriber = new CustomTestSubscriber<> ( );
        clientService.findClientsWithAccountsByLogin(UserUtils.NO_X_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        ClientList clients = subscriber.assertNoErrorsThenWaitAndGetValue ( );
        assertThat (clients).isNull ( );
    }

    @Test
    public void findClientsWithAccountsByLogin_shouldFindOneRacineWithOneCheckingsAndOpeAccount() {
        CustomTestSubscriber<ClientList> subscriber = new CustomTestSubscriber<> ( );
        clientService.findClientsWithAccountsByLogin(UserUtils.SINGLE_RACINE_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        ClientList clients = subscriber.assertNoErrorsThenWaitAndGetValue ( );
        assertThat (clients).isNotNull ( );
        assertThat (clients.getClients ( )).isNotEmpty ( ).hasSize (1);
        List<Account> accounts = clients.getClients ( ).stream ( )
                .findAny()
                .map(Client::getAccounts)
                .orElseThrow(() -> new IllegalStateException("Impossible"));
        assertThat(accounts).isNotEmpty()
                .containsExactly(new Account("00000000001"), new Account("00000000002"));
    }

    @Test
    public void findClientsWithAccountsByLogin_shouldFindTwoRacineWithAccountsInFirstRacine() {
        CustomTestSubscriber<ClientList> subscriber = new CustomTestSubscriber<> ( );
        clientService.findClientsWithAccountsByLogin(UserUtils.COUPLE_RACINE_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        ClientList clients = subscriber.assertNoErrorsThenWaitAndGetValue ( );
        assertThat(clients.getClients()).isNotEmpty().hasSize(1);
        List<Account> accountsR1 = clients.getClients ( ).stream ( )
                .filter (c -> c.getClientTechnicalId ( ).equals ("A5R1"))
                .findAny()
                .map(Client::getAccounts)
                .orElseThrow(() -> new IllegalStateException("client id with A5R1 should exist"));
        assertThat(accountsR1).isNotEmpty()
                .containsExactly(new Account("00000000001"), new Account("00000000002"));
        List<Account> accountsR2 = clients.getClients ( ).stream ( )
                .filter (c -> c.getClientTechnicalId ( ).equals ("A5R2"))
                .findAny()
                .map(Client::getAccounts)
                .orElseThrow(() -> new IllegalStateException("client id with A5R2 should exist"));
        assertThat(accountsR2).isEmpty();
    }

    @Test
    public void findClientsWithAccountsByLogin_sorted() {
        CustomTestSubscriber<ClientList> subscriber = new CustomTestSubscriber<>();
        clientService.findClientsWithAccountsByLogin(UserUtils.A6_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        ClientList clients = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(clients.getClients()).isNotEmpty().hasSize(8);
        assertThat(clients.getClients().stream()
                .findFirst().get().getClientTechnicalId()).isEqualTo("A6R2");
    }

}
