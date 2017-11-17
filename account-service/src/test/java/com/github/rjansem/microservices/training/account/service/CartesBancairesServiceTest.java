package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.domain.pbi.cartebancaire.CreditCard;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.apisecurity.Authority;
import com.github.rjansem.microservices.training.commons.testing.CustomTestSubscriber;
import com.github.rjansem.microservices.training.commons.testing.UserUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests associés au service cartebancaire
 *
 * @author mbouhamyd
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
public class CartesBancairesServiceTest {

    private AuthenticatedUser user = new AuthenticatedUser("token", "A1",
            Stream.of("Racine").collect(Collectors.toList()), Stream.of(Authority.CONSULTATION).collect(Collectors.toList()));

    @Autowired
    private CartesBancairesService cartesBancairesService;

    @Test
    public void findCarteBancaireById_shouldNotFindTheCard() {
        CustomTestSubscriber<CreditCard> subscriber = new CustomTestSubscriber<>();
        cartesBancairesService.findCCWithDetailById("", "811", "A1R1", user).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
    }

    @Test
    public void findCarteBancaireByIdA1_shouldFindOneCard() {
        CustomTestSubscriber<CreditCard> subscriber = new CustomTestSubscriber<>();
        cartesBancairesService.findCCWithDetailById("FR7630788001002504780000311", "111", UserUtils.A1_USER.getRacines().get(0), UserUtils.A1_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        CreditCard creditCard = subscriber.assertNoErrorsThenWaitAndGetValue();
        System.out.print(creditCard);
        assertThat(creditCard.getCreditcardId().toString()).isEqualTo("111");
        assertThat(creditCard.getCardNumber().toString()).isEqualTo("100250478XXXXXX1");
        assertThat(creditCard.getCardBalance().toString()).isEqualTo("-14.000000");
        assertThat(creditCard.getCardBalanceDate().toString()).isEqualTo("2017-11-11T00:00:00.000Z");
        assertThat(creditCard.getCardCurrency().toString()).isEqualTo("EUR");
        assertThat(creditCard.getDate().toString()).isEqualTo("2016-12-01");

    }

    @Test
    public void findCartesBancairesByIbanA1_shouldFindTwoCards() {
        CustomTestSubscriber<Set<CreditCard>> subscriber = new CustomTestSubscriber<>();
        cartesBancairesService.findAllCCWithDetailByIban("FR7630788001002504780000311", UserUtils.A1_USER.getRacines().get(0), UserUtils.A1_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<CreditCard> creditCards = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(creditCards).isNotEmpty().hasSize(1);
        Set<CreditCard> creditCardsExcpected = new HashSet<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.FRANCE);
        CreditCard creditCard = new CreditCard("111", "100250478XXXXXX1", "ISA CLASSIC internationale", "M DOE CHRISTIAN", new BigDecimal("14"), "2016-12-01T00:00:00Z", "EUR", "2017-04-28");
        creditCardsExcpected.add(creditCard);
        assertThat(creditCards.containsAll(creditCardsExcpected));
    }

    @Test
    public void findCartesBancairesByIbanA3_shouldFindTwoCards() {
        CustomTestSubscriber<Set<CreditCard>> subscriber = new CustomTestSubscriber<>();
        cartesBancairesService.findAllCCWithDetailByIban("FR7630788001002504780000311", UserUtils.A3_USER.getRacines().get(0), UserUtils.A3_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        Set<CreditCard> creditCards = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(creditCards).isNotEmpty().hasSize(2);
        Set<CreditCard> creditCardsExcpected = new HashSet<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.FRANCE);
        CreditCard creditCard = new CreditCard("111", "100250478XXXXXX1", "ISA CLASSIC internationale", "M DOE CHRISTIAN", new BigDecimal("14"), "2017-04-28T00:00:00Z", "EUR", "2016-12-01");
        creditCardsExcpected.add(creditCard);
        CreditCard secondcreditCard = new CreditCard("113", "100250478XXXXXX3", "ISA CLASSIC internationale", "M DOE CHRISTIAN", new BigDecimal("15"), "2017-04-28T00:00:00Z", "EUR", "2016-12-01");
        creditCardsExcpected.add(secondcreditCard);
        assertThat(creditCards.containsAll(creditCardsExcpected));
    }


}