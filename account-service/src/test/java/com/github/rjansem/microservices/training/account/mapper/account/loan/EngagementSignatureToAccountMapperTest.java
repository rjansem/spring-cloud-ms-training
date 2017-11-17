package com.github.rjansem.microservices.training.account.mapper.account.loan;

import com.github.rjansem.microservices.training.account.domain.efs.credit.EngagementSignature;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.mapper.account.buloc.EngagementSignatureToAccountMapper;
import com.github.rjansem.microservices.training.commons.domain.utils.DateUtils;
import org.junit.Test;

import java.math.BigDecimal;

import static com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType.ENGAGEMENT_PAR_SIGNATURE;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Mapper transformant un {@link EngagementSignature} en {@link Account}.
 *
 * @author aazzerrifi
 * @see EngagementSignature
 * @see Account
 */
public class EngagementSignatureToAccountMapperTest {

    @Test
    public void map_shouldMapAllFields() {
        Account expected = new Account();
        expected.setId("someId");
        expected.setAccountNumber("someId");
        expected.setCurrency("EUR");
        expected.setNextWriteOffCurrency("EUR");
        expected.setBookedBalance(new BigDecimal("1800000.000000"));
        expected.setTypeId(ENGAGEMENT_PAR_SIGNATURE.getId());
        expected.setType(ENGAGEMENT_PAR_SIGNATURE.getLibelle());
        expected.setNextWriteOffAmount(new BigDecimal("5915.000000"));
        expected.setSubtype("CREDIT DE TRESORERIE");
        expected.setSubtypeId("CREDIT DE TRESORERIE");
        expected.setBookedBalanceCurrency("EUR");
        expected.setDate(DateUtils.convertEfsDateToLocalDate("31-12-2021"));
        EngagementSignature engagementSignature = new EngagementSignature ("someId", "CREDIT DE TRESORERIE", "31-12-2021",
                "1800000.000000", "EUR", "2000000.000000");
        engagementSignature.setDateDebut("30-12-2014");
        engagementSignature.setMontantProchaineCommission ("5915.000000");
        assertThat(new EngagementSignatureToAccountMapper().map(engagementSignature)).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void map_shouldMapMandatoryFields() {
        Account expected = new Account();
        expected.setId("someId");
        expected.setAccountNumber("someId");
        expected.setCurrency("EUR");
        expected.setNextWriteOffCurrency("EUR");
        expected.setBookedBalance(new BigDecimal("1800000.000000"));
        expected.setTypeId(ENGAGEMENT_PAR_SIGNATURE.getId());
        expected.setType(ENGAGEMENT_PAR_SIGNATURE.getLibelle());
        expected.setSubtype("CREDIT DE TRESORERIE");
        expected.setSubtypeId("CREDIT DE TRESORERIE");
        expected.setBookedBalanceCurrency("EUR");
        expected.setDate(DateUtils.convertEfsDateToLocalDate("31-12-2021"));
        EngagementSignature engagementSignature = new EngagementSignature ("someId", "CREDIT DE TRESORERIE", "31-12-2021",
                "1800000.000000", "EUR", "2000000.000000");
        engagementSignature.setDateDebut("30-12-2014");
        assertThat(new EngagementSignatureToAccountMapper().map(engagementSignature)).isEqualToComparingFieldByField(expected);
    }
}