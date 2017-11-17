package com.github.rjansem.microservices.training.transaction.service;

import com.github.rjansem.microservices.training.commons.domain.GenericContent;
import com.github.rjansem.microservices.training.commons.testing.CustomTestSubscriber;
import com.github.rjansem.microservices.training.commons.testing.UserUtils;
import com.github.rjansem.microservices.training.transaction.domain.pbi.AddressBook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests associés au service {@link RetrieveABookService}
 *
 * @author aazzerrifi
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
public class RetrieveABookServiceTest {

    @Autowired
    private RetrieveABookService retrieveABookService;

    @Test
    public void findAllBeneficiares_findAccount_A1() throws Exception {
        CustomTestSubscriber<GenericContent<AddressBook>> subscriber = new CustomTestSubscriber<>();
        retrieveABookService.findAllBeneficiaires(UserUtils.A1_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        GenericContent<AddressBook> retrieveABook = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(retrieveABook.getContent()).isNotNull();
        assertThat(retrieveABook.getContent().stream().findFirst().get().getAccountId()).isEqualTo("FR7630788001001010689671817");
        assertThat(retrieveABook.getContent().stream().findFirst().get().getScheme()).isEqualTo("BBAN");
    }

    @Test
    public void findAllBeneficiares_shouldNotFindAccount() throws Exception {
        CustomTestSubscriber<GenericContent<AddressBook>> subscriber = new CustomTestSubscriber<>();
        retrieveABookService.findAllBeneficiaires(UserUtils.A2_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        GenericContent<AddressBook> retrieveABook = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(retrieveABook.getContent()).isEmpty();
    }

    @Test
    public void findAllBeneficiares_UNKNOWN_USER() throws Exception {
        CustomTestSubscriber<GenericContent<AddressBook>> subscriber = new CustomTestSubscriber<>();
        retrieveABookService.findAllBeneficiaires(UserUtils.UNKNOWN_USER)
                .doOnError(throwable -> {
                    assertEquals("Recherche des beneficiares du client UNKNOWN de l'application", throwable.getMessage());
                    fail(throwable.getMessage());
                }).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        assertThat(subscriber.getOnErrorEvents().isEmpty());
    }

    @Test
    public void findAllBeneficiares_A3() throws Exception {
        CustomTestSubscriber<GenericContent<AddressBook>> subscriber = new CustomTestSubscriber<>();
        retrieveABookService.findAllBeneficiaires(UserUtils.A3_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        GenericContent<AddressBook> retrieveABook = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(retrieveABook.getContent()).isNotNull();
        assertThat(retrieveABook.getContent().stream().findFirst().get().getAccountId()).isEqualTo("FR7630788001001010689671817");
        assertThat(retrieveABook.getContent().stream().findFirst().get().getScheme()).isEqualTo("BBAN");
    }

    @Test
    public void findAllBeneficiares_UNKNOW() throws Exception {
        CustomTestSubscriber<GenericContent<AddressBook>> subscriber = new CustomTestSubscriber<>();
        retrieveABookService.findAllBeneficiaires(UserUtils.UNKNOWN_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
    }
}