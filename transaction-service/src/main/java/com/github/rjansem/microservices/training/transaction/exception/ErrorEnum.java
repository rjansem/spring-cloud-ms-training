package com.github.rjansem.microservices.training.transaction.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;

import static com.github.rjansem.microservices.training.transaction.exception.ExceptionConstants.*;

/**
 * @author rjansem
 * @author aazzerrifi
 */
public enum ErrorEnum {

    TRANSACTION_100(CODE_EFS_100, CODE_PBI_100, TECHNECAL_MSG, HttpStatus.OK),
    TRANSACTION_101(CODE_EFS_101, CODE_PBI_101, TECHNECAL_MSG, HttpStatus.OK),
    TRANSACTION_102(CODE_EFS_102, CODE_PBI_102, TECHNECAL_MSG, HttpStatus.OK),
    TRANSACTION_103(CODE_EFS_103, CODE_PBI_103, TECHNECAL_MSG, HttpStatus.OK),
    TRANSACTION_104(CODE_EFS_104, CODE_PBI_104, MSG_PBI_PLAFOND, HttpStatus.OK),
    TRANSACTION_105(CODE_EFS_105, CODE_PBI_105, MSG_FORMAT, HttpStatus.OK),
    TRANSACTION_106(CODE_EFS_106, CODE_PBI_106, MSG_FORMAT, HttpStatus.OK),
    TRANSACTION_107_EXECUTION(CODE_EFS_107_EXECUTION, CODE_PBI_107, TECHNECAL_MSG, HttpStatus.OK),
    TRANSACTION_107_FORMAT(CODE_EFS_107_FORMAT, CODE_PBI_107, TECHNECAL_MSG, HttpStatus.OK),
    TRANSACTION_108(CODE_EFS_108, CODE_PBI_108, TECHNECAL_MSG, HttpStatus.OK),
    TRANSACTION_109(CODE_EFS_109, CODE_PBI_109, MSG_PBI_PLAFOND, HttpStatus.OK),
    TRANSACTION_110(CODE_EFS_110, CODE_PBI_110, MSG_PBI_PLAFOND, HttpStatus.OK),
    TRANSACTION_111(CODE_EFS_111, CODE_PBI_111, TECHNECAL_MSG, HttpStatus.OK),
    TRANSACTION_112(CODE_EFS_112, CODE_PBI_112, TECHNECAL_MSG, HttpStatus.INTERNAL_SERVER_ERROR),
    TRANSACTION_113(CODE_EFS_113, CODE_PBI_113, MSG_FORMAT, HttpStatus.OK),
    TRANSACTION_114(CODE_EFS_114, CODE_PBI_114, MSG_FORMAT, HttpStatus.OK),
    TRANSACTION_115(CODE_EFS_115, CODE_PBI_115, MSG_PBI_115, HttpStatus.OK),
    TRANSACTION_200(CODE_EFS_200, CODE_PBI_200, TECHNECAL_MSG, HttpStatus.OK),
    TRANSACTION_201(CODE_EFS_201, CODE_PBI_201, MSG_201, HttpStatus.OK),
    TRANSACTION_202(CODE_EFS_202, CODE_PBI_202, TECHNECAL_MSG, HttpStatus.FORBIDDEN),
    TRANSACTION_203(CODE_EFS_203, CODE_PBI_203, MSG_203, HttpStatus.OK),
    TRANSACTION_204(CODE_EFS_204, CODE_PBI_204, MSG_204, HttpStatus.OK),
    TRANSACTION_205(CODE_EFS_205, CODE_PBI_205, TECHNECAL_MSG, HttpStatus.INTERNAL_SERVER_ERROR),
    TRANSACTION_205_1(CODE_EFS_205_1, CODE_PBI_205, TECHNECAL_MSG, HttpStatus.INTERNAL_SERVER_ERROR),
    TRANSACTION_205_3(CODE_EFS_205_3, CODE_PBI_205, TECHNECAL_MSG, HttpStatus.INTERNAL_SERVER_ERROR),
    TRANSACTION_205_4(CODE_EFS_205_4, CODE_PBI_205, TECHNECAL_MSG, HttpStatus.INTERNAL_SERVER_ERROR),
    TRANSACTION_205_5(CODE_EFS_205_5, CODE_PBI_205, TECHNECAL_MSG, HttpStatus.INTERNAL_SERVER_ERROR),
    TRANSACTION_205_6(CODE_EFS_205_6, CODE_PBI_205, TECHNECAL_MSG, HttpStatus.INTERNAL_SERVER_ERROR),
    TRANSACTION_205_7(CODE_EFS_205_7, CODE_PBI_205, TECHNECAL_MSG, HttpStatus.INTERNAL_SERVER_ERROR),
    TRANSACTION_205_11(CODE_EFS_205_11, CODE_PBI_205, TECHNECAL_MSG, HttpStatus.INTERNAL_SERVER_ERROR),
    TRANSACTION_205_12(CODE_EFS_205_12, CODE_PBI_205, TECHNECAL_MSG, HttpStatus.INTERNAL_SERVER_ERROR),
    TRANSACTION_205_13(CODE_EFS_205_13, CODE_PBI_205, TECHNECAL_MSG, HttpStatus.INTERNAL_SERVER_ERROR),
    TRANSACTION_205_34(CODE_EFS_205_34, CODE_PBI_205, TECHNECAL_MSG, HttpStatus.INTERNAL_SERVER_ERROR),
    TRANSACTION_205_38(CODE_EFS_205_38, CODE_PBI_205, TECHNECAL_MSG, HttpStatus.INTERNAL_SERVER_ERROR),
    TRANSACTION_206(CODE_EFS_206, CODE_PBI_206, TECHNECAL_MSG, HttpStatus.OK),
    TRANSACTION_300(CODE_EFS_300, CODE_PBI_300, MSG_PBI_300, HttpStatus.OK),
    TRANSACTION_500("KBV", "KBV", "Vous n'êtes pas autorisé de signer ce virement", HttpStatus.OK);

    private String efsCode;

    private String pbiCode;

    private String pbiLabel;

    private HttpStatus httpStatus;

    ErrorEnum(String efsCode, String pbiCode, String pbiLabel, HttpStatus httpStatus) {
        this.efsCode = efsCode;
        this.pbiCode = pbiCode;
        this.pbiLabel = pbiLabel;
        this.httpStatus = httpStatus;
    }

    public static Optional<ErrorEnum> fromEfsCode(String efsCode) {
        return Arrays.stream(values()).filter(e -> efsCode.equals(e.getEfsCode())).findAny();
    }

    public String getEfsCode() {
        return efsCode;
    }

    public String getPbiCode() {
        return pbiCode;
    }

    public String getPbiLabel() {
        return pbiLabel;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
