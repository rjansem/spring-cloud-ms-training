package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.domain.pbi.portfolioAndLI.InvestissementDetail;
import com.github.rjansem.microservices.training.commons.testing.CustomTestSubscriber;
import com.github.rjansem.microservices.training.commons.testing.UserUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests associés au service portfolioandLI
 *
 * @author mbouhamyd
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
public class PortfolioAndLIServiceTest {

    @Autowired
    private PortfolioAndLIService portfolioAndLIService;

    //TODO vérifier tous les champs
    @Test
    public void findPortfolioWithAnyPoistion() throws Exception {
        CustomTestSubscriber<InvestissementDetail> subscriber = new CustomTestSubscriber<>();
        portfolioAndLIService.findPortfolio("01784500601", UserUtils.A1_USER.getRacines().get(0), UserUtils.A1_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        InvestissementDetail portfolio = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(portfolio.getType().toString()).isEqualTo("Portefeuille Titres");
        assertThat(portfolio.getBookedBalanceDate().toString()).isEqualTo("2015-07-19");
        assertThat(portfolio.getAssociatedAccounts()).isNotEmpty().hasSize(1);
        assertThat(portfolio.getAssociatedAccounts().stream().findFirst().get().getType().toString()).isEqualTo("Compte courant");
        assertThat(portfolio.getAssociatedAccounts().stream().findFirst().get().getId().toString()).isEqualTo("FR7630788001002504780000311");
        assertThat(portfolio.getAssetClassIdentification()).isEmpty();
        System.out.print(portfolio);
    }

    //TODO vérifier tous les champs
    @Test
    public void findPortfolio() throws Exception {
        CustomTestSubscriber<InvestissementDetail> subscriber = new CustomTestSubscriber<>();
        portfolioAndLIService.findPortfolio("01784500600", UserUtils.A1_USER.getRacines().get(0), UserUtils.A1_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        InvestissementDetail portfolio = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(portfolio.getType().toString()).isEqualTo("Portefeuille Titres");
        assertThat(portfolio.getBookedBalanceDate().toString()).isEqualTo("2015-07-19");
        assertThat(portfolio.getAssociatedAccounts()).isNotEmpty().hasSize(1);
        assertThat(portfolio.getAssociatedAccounts().stream().findFirst().get().getType().toString()).isEqualTo("Compte courant");
        assertThat(portfolio.getAssociatedAccounts().stream().findFirst().get().getId().toString()).isEqualTo("FR7630788001002504780000311");
        assertThat(portfolio.getAssetClassIdentification()).isNotEmpty().hasSize(3);
        System.out.print(portfolio);
    }

    //TODO vérifier tous les champs
    @Test
    public void findLifeAssurance() throws Exception {
        CustomTestSubscriber<InvestissementDetail> subscriber = new CustomTestSubscriber<>();
        portfolioAndLIService.findLifeInsurance("80000001", UserUtils.A1_USER.getRacines().get(0), UserUtils.A1_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        InvestissementDetail assurance = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(assurance.getType().toString()).isEqualTo("Assurance vie");
        assertThat(assurance.getSubtypeId().toString()).isEqualTo("Externe");
        assertThat(assurance.getBookedBalanceDate().toString()).isEqualTo("2015-01-19");
        assertThat(assurance.getAssociatedAccounts()).isEmpty();
        assertThat(assurance.getAssetClassIdentification()).isNotEmpty().hasSize(1);
        assertThat(assurance.getAssetClassIdentification().stream().findFirst().get().getAssetClass().toString()).isEqualTo("Fonds en Euro");
        System.out.print(assurance);
    }

    //TODO vérifier tous les champs
    @Test
    public void findLifeAssuranceNOBC() throws Exception {
        CustomTestSubscriber<InvestissementDetail> subscriber = new CustomTestSubscriber<>();
        portfolioAndLIService.findLifeInsurance("10001090", UserUtils.A1_USER.getRacines().get(0), UserUtils.A1_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        InvestissementDetail assurance = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(assurance.getType().toString()).isEqualTo("Assurance vie");
        assertThat(assurance.getSubtypeId().toString()).isEqualTo("Interne");
        assertThat(assurance.getBookedBalanceDate().toString()).isEqualTo("2015-04-24");
        assertThat(assurance.getAssociatedAccounts()).isEmpty();
        assertThat(assurance.getAssetClassIdentification()).isNotEmpty().hasSize(1);
        assertThat(assurance.getAssetClassIdentification().stream().findFirst().get().getAssetClass().toString()).isEqualTo("Non classés");
        System.out.print(assurance);
    }

}