package com.github.rjansem.microservices.training.account.service;

import com.github.rjansem.microservices.training.account.domain.pbi.position.PositionDetail;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Tests associés au service positiondetail
 *
 * @author mbouhamyd
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
public class PositionDetailServiceTest {

    @Autowired
    private PositionDetailService positionDetailService;

    @Test
    public void positionDetailPortfolio() throws Exception {
        CustomTestSubscriber<PositionDetail> subscriber = new CustomTestSubscriber<>();
        positionDetailService.positionDetailPortfolio(UserUtils.A1_USER.getRacines().get(0), "01784500600", "FR0000422297", UserUtils.A1_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        PositionDetail positionDetailExcepted = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(positionDetailExcepted.getAccountNumber()).isEqualTo("01784500600");
        assertThat(positionDetailExcepted.getSubtype()).isEqualTo("COMPTE TITRE PEA GERE");
        assertThat(positionDetailExcepted.getSubtypeId()).isEqualTo("PEA_GERE");
        assertThat(positionDetailExcepted.getPositionValue()).isEqualTo("ULTIMA PEA I 4DEC");
        assertThat(positionDetailExcepted.getPositionValueCode()).isEqualTo("FR0000422297");
        BigDecimal returnOnInvestment = new BigDecimal("551.76814");
        BigDecimal estimation = new BigDecimal("76567.4");
        BigDecimal percentage = new BigDecimal("98.84");
        assertThat(positionDetailExcepted.getAmount()).isEqualTo(new BigDecimal("140.0"));
        assertEquals(positionDetailExcepted.getReturnOnInvestment(), returnOnInvestment);
        assertEquals(positionDetailExcepted.getEstimation(), estimation);
        assertThat(positionDetailExcepted.getReturnOnInvestmentCurrency()).isEqualTo("EUR");
        assertThat(positionDetailExcepted.getEstimationCurrency()).isEqualTo("EUR");
        assertEquals(positionDetailExcepted.getPercentage(), percentage);
        assertThat(positionDetailExcepted.getDate()).isEqualTo(DateUtils.convertEfsDateToPibLocalDateString("16-07-2015"));
    }

    @Test
    public void PositionDetailLifeAssurance() throws Exception {
        CustomTestSubscriber<PositionDetail> subscriber = new CustomTestSubscriber<>();
        positionDetailService.positionDetailLifeAssurance(UserUtils.A1_USER.getRacines().get(0), "80000001", "EXT198", UserUtils.A1_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        PositionDetail positionDetailExcepted = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(positionDetailExcepted.getAccountNumber()).isEqualTo("80000001");
        assertThat(positionDetailExcepted.getSubtype()).isEqualTo("LIFE MOBILITY EVOLUTION VIE");
        assertThat(positionDetailExcepted.getSubtypeId()).isEqualTo("Externe");
        assertThat(positionDetailExcepted.getPositionValue()).isEqualTo("ACTIF REGULARITE");
        assertThat(positionDetailExcepted.getPositionValueCode()).isEqualTo("EXT198");
        BigDecimal estimation = new BigDecimal("2315359.01");
        BigDecimal percentage = new BigDecimal("6.3");
        assertThat(positionDetailExcepted.getAmount()).isEqualTo(new BigDecimal("2315359.01"));
        assertEquals(positionDetailExcepted.getReturnOnInvestment(), null);
        assertEquals(positionDetailExcepted.getEstimation(), estimation);
        assertThat(positionDetailExcepted.getReturnOnInvestmentCurrency()).isEqualTo(null);
        assertThat(positionDetailExcepted.getEstimationCurrency()).isEqualTo("EUR");
        assertEquals(positionDetailExcepted.getPercentage(), percentage);
        assertThat(positionDetailExcepted.getDate()).isEqualTo(DateUtils.convertEfsDateToPibLocalDateString("31-07-2015"));
    }

    @Test
    public void PositionDetailLifeAssuranceNOBC() throws Exception {
        CustomTestSubscriber<PositionDetail> subscriber = new CustomTestSubscriber<>();
        positionDetailService.positionDetailLifeAssurance(UserUtils.A1_USER.getRacines().get(0), "10001090", "LU0321538950", UserUtils.A1_USER).subscribe(subscriber);
        assertThat(subscriber.getOnNextEvents().isEmpty());
        PositionDetail positionDetailExpected = subscriber.assertNoErrorsThenWaitAndGetValue();
        assertThat(positionDetailExpected.getAccountNumber()).isEqualTo("10001090");
        assertThat(positionDetailExpected.getSubtype()).isEqualTo("Hoche Patrimoine Capitalisation 2ème génération");
        assertThat(positionDetailExpected.getSubtypeId()).isEqualTo("Interne");
        assertThat(positionDetailExpected.getPositionValue()).isEqualTo("AA MMF FOM NORTH AMERICAN EQ");
        assertThat(positionDetailExpected.getPositionValueCode()).isEqualTo("LU0321538950");
        BigDecimal returnOnInvestment = new BigDecimal("67.7300000");
        BigDecimal estimation = new BigDecimal("41399.5500000");
        BigDecimal percentage = new BigDecimal("6.3500000");
        assertThat(positionDetailExpected.getAmount()).isEqualTo(new BigDecimal("467.0000000"));
        assertEquals(positionDetailExpected.getReturnOnInvestment(), returnOnInvestment);
        assertEquals(positionDetailExpected.getEstimation(), estimation);
        assertThat(positionDetailExpected.getReturnOnInvestmentCurrency()).isEqualTo("EUR");
        assertThat(positionDetailExpected.getEstimationCurrency()).isEqualTo("EUR");
        assertEquals(positionDetailExpected.getPercentage(), percentage);
        assertThat(positionDetailExpected.getDate()).isEqualTo(DateUtils.convertEfsDateToPibLocalDateString("20-02-2015"));
    }


}