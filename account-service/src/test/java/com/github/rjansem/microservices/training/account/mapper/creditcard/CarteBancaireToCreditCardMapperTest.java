package com.github.rjansem.microservices.training.account.mapper.creditcard;

import com.github.rjansem.microservices.training.account.domain.efs.cartebancaire.CarteBancaire;
import com.github.rjansem.microservices.training.account.domain.pbi.cartebancaire.CreditCard;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Mapper transformant une carte bancaire en creditcard
 *
 * @author mbouhamyd
 */
public class CarteBancaireToCreditCardMapperTest {
    @Test
    public void map_shouldMapAllFields() {
        CarteBancaire cb = new CarteBancaire(111, "VISA CLASSIC internationale", "1002504780000351", "M DOE CHRISTIAN", "-14.000000"
                , 10, "ACT", "28-04-2017", "FR7630788001002504780000351", "NADAL CHRISTIAN", "01-12-2016");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        formatter = formatter.withLocale(Locale.FRANCE);
        LocalDate prel = LocalDate.parse("01-12-2016", formatter);
        LocalDate exp = LocalDate.parse("28-04-2017", formatter);
        CreditCard expected = new CreditCard("111", "100250478XXXXXX1", "VISA CLASSIC internationale", "NADAL CHRISTIAN", new BigDecimal("-14.000000"), null, "10", "2016-12-01");
        assertThat(new CarteBancaireToCreditCardMapper().map(cb)).isEqualToComparingFieldByField(expected);
        System.out.println(expected);
    }
}