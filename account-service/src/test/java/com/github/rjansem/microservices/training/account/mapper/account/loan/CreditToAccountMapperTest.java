package com.github.rjansem.microservices.training.account.mapper.account.loan;


import com.github.rjansem.microservices.training.account.domain.efs.credit.Credit;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType.CREDIT;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Mapper transformant un {@link Credit} en {@link Account}.
 *
 * @author aazzerrifi
 * @see Credit
 * @see Account
 */
public class CreditToAccountMapperTest {

    @Test
    public void map_shouldMapAllFields() {
        Account expected = new Account();
        expected.setId("0023514/001");
        expected.setAccountNumber("0023514/001");
        expected.setCurrency("EUR");
        expected.setNextWriteOffCurrency("EUR");
        expected.setBookedBalanceCurrency("EUR");
        expected.setBookedBalance(new BigDecimal("1800000.000000"));
        expected.setTypeId(CREDIT.getId());
        expected.setType(CREDIT.getLibelle());
        expected.setSubtype("CREDIT DE TRESORERIE");
        expected.setSubtypeId("CREDIT DE TRESORERIE");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        formatter = formatter.withLocale(Locale.FRANCE);
        LocalDate date = LocalDate.parse("31-03-2016", formatter);
        expected.setDate(date);
        expected.setNextWriteOffAmount(new BigDecimal("5915.000000"));
        Credit credit = new Credit("0023514/001", "CREDIT DE TRESORERIE", "31-12-2021",
                "1800000.000000", "EUR", "2000000.000000");
        credit.setDateProchaineEcheance("31-03-2016");
        credit.setDateDebut ("30-12-2014");
        credit.setMontantProchaineEcheance("5915.000000");
        assertThat(new CreditToAccountMapper().map(credit)).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void map_shouldMapMandatoryFields() {
        Account expected = new Account();
        expected.setId("0023514/001");
        expected.setAccountNumber("0023514/001");
        expected.setCurrency("EUR");
        expected.setNextWriteOffCurrency("EUR");
        expected.setBookedBalanceCurrency("EUR");
        expected.setBookedBalance(new BigDecimal("1800000.000000"));
        expected.setTypeId(CREDIT.getId());
        expected.setType(CREDIT.getLibelle());
        expected.setSubtype("CREDIT DE TRESORERIE");
        expected.setSubtypeId("CREDIT DE TRESORERIE");
        Credit credit = new Credit("0023514/001", "CREDIT DE TRESORERIE", "31-12-2021",
                "1800000.000000", "EUR", "2000000.000000");
        credit.setDateDebut ("30-12-2014");
        assertThat(new CreditToAccountMapper().map(credit)).isEqualToComparingFieldByField(expected);
    }
}