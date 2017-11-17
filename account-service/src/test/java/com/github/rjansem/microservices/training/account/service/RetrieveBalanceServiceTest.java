package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.domain.pbi.account.RetrieveBalance;
import com.github.rjansem.microservices.training.account.domain.pbi.cartebancaire.CreditCard;
import com.github.rjansem.microservices.training.apisecurity.AuthenticatedUser;
import com.github.rjansem.microservices.training.apisecurity.Authority;
import com.github.rjansem.microservices.training.commons.testing.CustomTestSubscriber;
import com.github.rjansem.microservices.training.commons.testing.UserUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * Tests associés au service retrieve balance
 *
 * @author mbouhamyd
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
public class RetrieveBalanceServiceTest {

    private AuthenticatedUser user = new AuthenticatedUser("token", "A1",
            Stream.of("Racine").collect(Collectors.toList()), Stream.of(Authority.CONSULTATION).collect(Collectors.toList()));

    @Autowired
    private RetrieveBalanceService retrieveBalanceService;

    @Test
    public void findRetrieveBalance_shouldNotFindTheBalanceRetrieve() {
        CustomTestSubscriber<List<RetrieveBalance>> subscriber = new CustomTestSubscriber<>();
        retrieveBalanceService.findRetrieveBalanceByRacine(UserUtils.UNKNOWN_EXCEPTION, UserUtils.UNKNOWN_EXCEPTION.getRacines().get(0)).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
    }

    @Test
    public void findRetrieveBalanceByRacineA1_shouldFindOneBalanceRetrieve() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        CustomTestSubscriber<List<RetrieveBalance>> subscriber = new CustomTestSubscriber<>();
        retrieveBalanceService.findRetrieveBalanceByRacine(UserUtils.A1_USER, UserUtils.A1_USER.getRacines().get(0)).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        List<RetrieveBalance> retrieveBalances = subscriber.assertNoErrorsThenWaitAndGetValue();
        stopWatch.stop();
        System.out.println("//////////////////////////////// " + stopWatch.getTime() + " /////////////////////////");
        assertThat(retrieveBalances).isNotEmpty().hasSize(1);
        RetrieveBalance retrieveBalanceExpected = new RetrieveBalance();
        retrieveBalanceExpected.setId("FR7630788001002504780000311");
        retrieveBalanceExpected.setType("Compte courant");
        retrieveBalanceExpected.setTypeId("DAV");
        retrieveBalanceExpected.setSubtype("COMPTE A VUE");
        retrieveBalanceExpected.setSubtypeId("CAV");
        retrieveBalanceExpected.setAcountNumber("00000000001");
        retrieveBalanceExpected.setIban("FR7630788001002504780000311");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        formatter = formatter.withLocale(Locale.FRANCE);
        retrieveBalanceExpected.setBookedBalanceDate("2017-11-11T00:00:00.000Z");
        retrieveBalanceExpected.setCreditcardTotalBalanceDate("2017-11-11T00:00:00.000Z");
        retrieveBalanceExpected.setCreditcardTotalBalance(new BigDecimal("-393.920000"));
        retrieveBalanceExpected.setCreditcardTotalCurrency("EUR");
        retrieveBalanceExpected.setBookedBalanceCurrency("EUR");
        retrieveBalanceExpected.setBookedBalance(BigDecimal.valueOf(1512.75));
        Set<CreditCard> creditCards = new HashSet<>();
        DateTimeFormatter formatterPBI = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatterPBI = formatterPBI.withLocale(Locale.FRANCE);
        CreditCard creditCard = new CreditCard("111", "1002504780000311", "ISA CLASSIC internationale", "M DOE CHRISTIAN", new BigDecimal("-14.000000"), "2016-12-01T00:00:00Z", "EUR", "2017-04-28T00:00:00Z");
        creditCards.add(creditCard);
        retrieveBalanceExpected.setCreditCards(creditCards);
        assertThat(retrieveBalances.stream().findFirst().orElseThrow(() -> new IllegalStateException("Impossible")))
                .isEqualToComparingFieldByField(retrieveBalanceExpected);
    }

    @Test
    public void findRetrieveBalanceByRacineA2_shouldFindTwoBalanceRetrieve() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        CustomTestSubscriber<List<RetrieveBalance>> subscriber = new CustomTestSubscriber<>();
        retrieveBalanceService.findRetrieveBalanceByRacine(UserUtils.A2_USER, UserUtils.A2_USER.getRacines().get(0)).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        List<RetrieveBalance> retrieveBalances = subscriber.assertNoErrorsThenWaitAndGetValue();
        stopWatch.stop();
        System.out.println("//////////////////////////////// " + stopWatch.getTime() + " /////////////////////////");
        assertThat(retrieveBalances).isNotEmpty().hasSize(2);
        RetrieveBalance firstretrieveBalanceExpected = new RetrieveBalance();
        firstretrieveBalanceExpected.setId("FR7630788001002504780000311");
        firstretrieveBalanceExpected.setType("Compte courant");
        firstretrieveBalanceExpected.setTypeId("DAV");
        firstretrieveBalanceExpected.setSubtype("COMPTE A VUE");
        firstretrieveBalanceExpected.setSubtypeId("COMPTE A VUE");
        firstretrieveBalanceExpected.setAcountNumber("00000000001");
        firstretrieveBalanceExpected.setIban("FR7630788001002504780000311");
        firstretrieveBalanceExpected.setBookedBalanceCurrency("EUR");
        firstretrieveBalanceExpected.setBookedBalance(BigDecimal.valueOf(1512.75));
        Set<CreditCard> creditCards = new HashSet<>();
        CreditCard creditCard = new CreditCard("111", "1002504780000311", "ISA CLASSIC internationale", "M DOE CHRISTIAN", new BigDecimal("-14.000000"), "2017-04-28T00:00:00Z", "EUR", "2016-12-01T00:00:00Z");
        creditCards.add(creditCard);
        firstretrieveBalanceExpected.setCreditCards(creditCards);
        RetrieveBalance secondRetrieveBalanceExpected = new RetrieveBalance();
        secondRetrieveBalanceExpected.setId("FR7630788001002504780000353");
        secondRetrieveBalanceExpected.setType("Compte epargne");
        secondRetrieveBalanceExpected.setTypeId("Epargne");
        secondRetrieveBalanceExpected.setSubtype("PEA");
        secondRetrieveBalanceExpected.setSubtypeId("PEA");
        secondRetrieveBalanceExpected.setAcountNumber("00000000003");
        secondRetrieveBalanceExpected.setIban("FR7630788001002504780000353");
        secondRetrieveBalanceExpected.setBookedBalanceCurrency("EUR");
        secondRetrieveBalanceExpected.setBookedBalance(BigDecimal.valueOf(5512.75));
        Set<RetrieveBalance> retrieveBalancesExcepted = new HashSet<>();
        retrieveBalancesExcepted.add(firstretrieveBalanceExpected);
        retrieveBalancesExcepted.add(secondRetrieveBalanceExpected);
        assertThat(retrieveBalances.containsAll(retrieveBalancesExcepted));
    }

    @Test
    public void findRetrieveBalanceByRacineA3_shouldFindTreeBalanceRetrieve() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        CustomTestSubscriber<List<RetrieveBalance>> subscriber = new CustomTestSubscriber<>();
        retrieveBalanceService.findRetrieveBalanceByRacine(UserUtils.A3_USER, UserUtils.A3_USER.getRacines().get(0)).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        List<RetrieveBalance> retrieveBalances = subscriber.assertNoErrorsThenWaitAndGetValue();
        stopWatch.stop();
        System.out.println("//////////////////////////////// " + stopWatch.getTime() + " /////////////////////////");
        assertThat(retrieveBalances).isNotEmpty().hasSize(3);
        RetrieveBalance firstretrieveBalanceExpected = new RetrieveBalance();
        firstretrieveBalanceExpected.setId("FR7630788001002504780000311");
        firstretrieveBalanceExpected.setType("Compte courant");
        firstretrieveBalanceExpected.setTypeId("DAV");
        firstretrieveBalanceExpected.setSubtype("COMPTE A VUE");
        firstretrieveBalanceExpected.setSubtypeId("COMPTE A VUE");
        firstretrieveBalanceExpected.setAcountNumber("00000000001");
        firstretrieveBalanceExpected.setIban("FR7630788001002504780000311");
        firstretrieveBalanceExpected.setBookedBalanceCurrency("EUR");
        firstretrieveBalanceExpected.setBookedBalance(BigDecimal.valueOf(1512.75));
        Set<CreditCard> creditCards = new HashSet<>();
        CreditCard creditCard = new CreditCard("111", "1002504780000311", "ISA CLASSIC internationale", "M DOE CHRISTIAN", new BigDecimal("-14.000000"), "2017-04-28T00:00:00Z", "EUR", "2016-12-01T00:00:00Z");
        creditCards.add(creditCard);
        firstretrieveBalanceExpected.setCreditCards(creditCards);
        RetrieveBalance secondRetrieveBalanceExpected = new RetrieveBalance();
        secondRetrieveBalanceExpected.setId("FR7630788001002504780000353");
        secondRetrieveBalanceExpected.setType("Compte epargne");
        secondRetrieveBalanceExpected.setTypeId("Epargne");
        secondRetrieveBalanceExpected.setSubtype("PEA");
        secondRetrieveBalanceExpected.setSubtypeId("PEA");
        secondRetrieveBalanceExpected.setAcountNumber("00000000003");
        secondRetrieveBalanceExpected.setIban("FR7630788001002504780000353");
        secondRetrieveBalanceExpected.setBookedBalanceCurrency("EUR");
        secondRetrieveBalanceExpected.setBookedBalance(BigDecimal.valueOf(5512.75));
        Set<RetrieveBalance> retrieveBalancesExcepted = new HashSet<>();
        retrieveBalancesExcepted.add(firstretrieveBalanceExpected);
        retrieveBalancesExcepted.add(secondRetrieveBalanceExpected);
        RetrieveBalance ThirtyretrieveBalanceExpected = new RetrieveBalance();
        ThirtyretrieveBalanceExpected.setId("FR7630788001002504780000312");
        ThirtyretrieveBalanceExpected.setType("Compte courant");
        ThirtyretrieveBalanceExpected.setTypeId("DAV");
        ThirtyretrieveBalanceExpected.setSubtype("COMPTE A VUE");
        ThirtyretrieveBalanceExpected.setSubtypeId("COMPTE A VUE");
        ThirtyretrieveBalanceExpected.setAcountNumber("00000000002");
        ThirtyretrieveBalanceExpected.setIban("FR7630788001002504780000312");
        ThirtyretrieveBalanceExpected.setBookedBalanceCurrency("EUR");
        ThirtyretrieveBalanceExpected.setBookedBalance(BigDecimal.valueOf(1512.75));
        Set<CreditCard> firstcreditCards = new HashSet<>();
        CreditCard firstcreditCard = new CreditCard("112", "1002504780000312", "ISA CLASSIC internationale", "M DOE CHRISTIAN", new BigDecimal("-14.000000"), "2017-04-28T00:00:00Z", "EUR", "2016-12-01T00:00:00Z");
        firstcreditCards.add(firstcreditCard);
        ThirtyretrieveBalanceExpected.setCreditCards(firstcreditCards);
        assertThat(retrieveBalances.containsAll(retrieveBalancesExcepted));

    }

    @Test
    public void findOneRetrieveBalanceByIdAccountA1_shouldFindBalanceRetrieve() {
        CustomTestSubscriber<RetrieveBalance> subscriber = new CustomTestSubscriber<>();
        retrieveBalanceService.findOneRetrieveBalanceByIdAccount("FR7630788001002504780000311", "DAV", UserUtils.A1_USER, UserUtils.A1_USER.getRacines().get(0)).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        RetrieveBalance retrieveBalances = subscriber.assertNoErrorsThenWaitAndGetValue();
        RetrieveBalance retrieveBalanceExpected = new RetrieveBalance();
        assertThat(retrieveBalances.getId().toString()).isEqualTo("FR7630788001002504780000311");
        assertThat(retrieveBalances.getType().toString()).isEqualTo("Compte courant");
        assertThat(retrieveBalances.getTypeId().toString()).isEqualTo("DAV");
        assertThat(retrieveBalances.getSubtype().toString()).isEqualTo("COMPTE A VUE");
        assertThat(retrieveBalances.getSubtypeId().toString()).isEqualTo("CAV");
        assertThat(retrieveBalances.getAccountNumber().toString()).isEqualTo("00000000001");
        assertThat(retrieveBalances.getIban().toString()).isEqualTo("FR7630788001002504780000311");
        assertThat(retrieveBalances.getBookedBalance().toString()).isEqualTo("1512.75");
        assertThat(retrieveBalances.getBookedBalanceDate().toString()).isEqualTo("2017-11-11T00:00:00.000Z");
        assertThat(retrieveBalances.getBookedBalanceCurrency().toString()).isEqualTo("EUR");
        Set<CreditCard> creditCardsExpected = new HashSet<>();
        CreditCard creditCard = new CreditCard();
        creditCard.setCreditcardId("111");
        creditCard.setCardNumber("1002504780000351");
        creditCard.setCardBalance(new BigDecimal("-14.000000"));
        creditCard.setCardCurrency("EUR");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.FRANCE);
        creditCard.setCardBalanceDate("2012-12-01T00:00:00Z");
        creditCard.setDate("2017-04-28T00:00:00Z");
        creditCardsExpected.add(creditCard);
        CreditCard secondcreditCard = new CreditCard();
        secondcreditCard.setCreditcardId("113");
        secondcreditCard.setCardNumber("1002504780000313");
        secondcreditCard.setCardBalance(new BigDecimal("-14.000000"));
        secondcreditCard.setCardCurrency("EUR");
        secondcreditCard.setCardBalanceDate("2012-12-01T00:00:00Z");
        secondcreditCard.setDate("2017-04-28T00:00:00Z");
        creditCardsExpected.add(secondcreditCard);
        retrieveBalanceExpected.setCreditCards(creditCardsExpected);
        assertThat(retrieveBalances.getCreditcards().containsAll(creditCardsExpected));
    }

    @Test
    public void findOneRetrieveBalanceByIdAccountA2_shouldFindBalanceRetrieve() {
        CustomTestSubscriber<RetrieveBalance> subscriber = new CustomTestSubscriber<>();
        retrieveBalanceService.findOneRetrieveBalanceByIdAccount("FR7630788001002504780000353", "Epargne", UserUtils.A2_USER, UserUtils.A2_USER.getRacines().get(0)).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        RetrieveBalance retrieveBalances = subscriber.assertNoErrorsThenWaitAndGetValue();
        RetrieveBalance retrieveBalanceExpected = new RetrieveBalance();
        assertThat(retrieveBalances.getId().toString()).isEqualTo("FR7630788001002504780000353");
        assertThat(retrieveBalances.getType().toString()).isEqualTo("Compte épargne");
        assertThat(retrieveBalances.getTypeId().toString()).isEqualTo("Epargne");
        assertThat(retrieveBalances.getSubtype().toString()).isEqualTo("PEA");
        assertThat(retrieveBalances.getSubtypeId().toString()).isEqualTo("PEA");
        assertThat(retrieveBalances.getAccountNumber().toString()).isEqualTo("00000000003");
        assertThat(retrieveBalances.getIban().toString()).isEqualTo("FR7630788001002504780000353");
        assertThat(retrieveBalances.getBookedBalance().toString()).isEqualTo("5512.75");
        assertThat(retrieveBalances.getBookedBalanceDate().toString()).isEqualTo("2017-11-11T00:00:00.000Z");
        assertThat(retrieveBalances.getBookedBalanceCurrency().toString()).isEqualTo("EUR");
    }

    @Test(expected = IllegalArgumentException.class)
    public void findOneRetrieveBalanceByIdAccount_shouldFailAccoutTypeInvalid() {
        CustomTestSubscriber<RetrieveBalance> subscriber = new CustomTestSubscriber<>();
        retrieveBalanceService.findOneRetrieveBalanceByIdAccount("FR7630788001002504780000311", "03", UserUtils.A1_USER, UserUtils.A1_USER.getRacines().get(0)).subscribe(subscriber);
        fail("le AccountType est invalide");
    }

}