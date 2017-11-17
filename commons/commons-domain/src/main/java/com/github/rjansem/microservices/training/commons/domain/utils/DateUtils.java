package com.github.rjansem.microservices.training.commons.domain.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utilitaires de mapping de dates
 *
 * @author jntakpe
 */
public final class DateUtils {

    public static final String PIB_DATE_FORMAT_ISO = "yyyy-MM-dd'T'HH:mm:'00+0000'";
    public static final String PIB_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:'00.000Z'";
    private static final String EFS_DATE_FORMAT = "dd-MM-yyyy";
    public static final String EFS_POST_DATE_FORMAT = "ddMMyyyy";

    private DateUtils() {
    }

    public static String convertEfsDateToPibLocalDateString(String efsDate) {
        return StringUtils.isBlank(efsDate) ? null : LocalDate.parse(efsDate, DateTimeFormatter.ofPattern(EFS_DATE_FORMAT)).toString();
    }

    public static String convertPbiDateTimeToEfsPostDateString(String pbiDate) {
        ZonedDateTime parse = ZonedDateTime.parse(pbiDate);
        return parse.format(DateTimeFormatter.ofPattern(EFS_POST_DATE_FORMAT));
    }

    public static LocalDate convertEfsDateToLocalDate(String efsDate) {
        return StringUtils.isBlank(efsDate) ? null : LocalDate.parse(efsDate, DateTimeFormatter.ofPattern(EFS_DATE_FORMAT));
    }

    public static String convertEfsDateToPibDate(String efsDate) {
        LocalDate localDate = convertEfsDateToLocalDate(efsDate);
        if (localDate == null) {
            return null;
        }
        return localDate.atStartOfDay().format(DateTimeFormatter.ofPattern(PIB_DATE_FORMAT));
    }

    public static String convertEfsDateToPibDateIso(String efsDate) {
        LocalDate localDate = convertEfsDateToLocalDate(efsDate);
        if (localDate == null) {
            return null;
        }
        return localDate.atStartOfDay().format(DateTimeFormatter.ofPattern(PIB_DATE_FORMAT_ISO));
    }

}
