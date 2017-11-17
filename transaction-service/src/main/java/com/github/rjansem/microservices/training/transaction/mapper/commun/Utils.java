package com.github.rjansem.microservices.training.transaction.mapper.commun;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author aazzerrifi
 *
 * // FIXME Code review Rudy : à intégrer au date utils dans common-domain. Eviter les noms de classe trop générique par ailleurs
 */
public class Utils {

    public static final String BBAN = "BBAN";

    public static final String IBAN = "IBAN";


    public static final String PIB_DATE_FORMAT_ISO = "yyyy-MM-dd'T'HH:mm:'00+000Z'";

    public static final String PIB_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:'00.000Z'";

    public static final String EFS_DATE_FORMAT = "dd-MM-yyyy";

    private Utils() {
    }

    public static String convertEfsDateToPibLocalDateString(String efsDate) {
        return StringUtils.isBlank(efsDate) ? null : LocalDate.parse(efsDate, DateTimeFormatter.ofPattern(EFS_DATE_FORMAT)).toString();
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

