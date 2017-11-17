package com.github.rjansem.microservices.training.account.mapper.account.commun;

import com.github.rjansem.microservices.training.commons.domain.utils.DateUtils;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests des utilitaires de date
 *
 * @author jntakpe
 */
public class DateUtilsTest {

    @Test
    public void convertStringToLocalDate_shouldReturnNull() {
        assertThat(DateUtils.convertEfsDateToLocalDate(null)).isNull();
    }

    @Test
    public void convertStringToLocalDate_shouldReturnNullIfEmpty() {
        assertThat(DateUtils.convertEfsDateToLocalDate("")).isNull();
    }

    @Test
    public void convertStringToLocalDate_shouldConvertString() {
        assertThat(DateUtils.convertEfsDateToLocalDate("01-10-2017")).isEqualTo(LocalDate.of(2017, 10, 1));
    }

    @Test(expected = DateTimeParseException.class)
    public void convertStringToLocalDate_shouldFailCuzIncorrectFormat() {
        DateUtils.convertEfsDateToLocalDate("01-16-2017");
    }

    @Test
    public void convertEfsDateToPibDate_shouldReturnNull() {
        assertThat(DateUtils.convertEfsDateToPibDate(null)).isNull();
    }

    @Test
    public void convertEfsDateToPibDate_shouldReturnNullIfEmpty() {
        assertThat(DateUtils.convertEfsDateToPibDate("")).isNull();
    }

    @Test
    public void convertEfsDateToPibDate_shouldConvertString() {
        assertThat(DateUtils.convertEfsDateToPibDate("01-10-2017")).isEqualTo("2017-10-01T00:00:00.000Z");
    }

    @Test(expected = DateTimeParseException.class)
    public void convertEfsDateToPibDate_shouldFailCuzIncorrectFormat() {
        DateUtils.convertEfsDateToPibDate("01-16-2017");
    }

    @Test
    public void convertEfsDateToPibDateIso_shouldReturnNull() {
        assertThat(DateUtils.convertEfsDateToPibDateIso(null)).isNull();
    }

    @Test
    public void convertEfsDateToPibDateIso_shouldReturnNullIfEmpty() {
        assertThat(DateUtils.convertEfsDateToPibDateIso("")).isNull();
    }

    @Test
    public void convertEfsDateToPibDateIso_shouldConvertString() {
        assertThat(DateUtils.convertEfsDateToPibDateIso("01-10-2017")).isEqualTo("2017-10-01T00:00:00+0000");
    }

    @Test(expected = DateTimeParseException.class)
    public void convertEfsDateToPibDateIso_shouldFailCuzIncorrectFormat() {
        DateUtils.convertEfsDateToPibDateIso("01-16-2017");
    }

}