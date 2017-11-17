package com.github.rjansem.microservices.training.transaction.exception;

import com.github.rjansem.microservices.training.exception.EfsCode;
import com.github.rjansem.microservices.training.exception.NOBCException;
import com.github.rjansem.microservices.training.exception.TechnicalCode;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.Alarme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.exceptions.CompositeException;

import java.util.Optional;

import static com.github.rjansem.microservices.training.exception.NOBCException.EFS_CODE;
import static com.github.rjansem.microservices.training.exception.NOBCException.EFS_MESSAGE;
import static com.github.rjansem.microservices.training.exception.NOBCException.wrapEfs;
import static com.github.rjansem.microservices.training.transaction.exception.ExceptionConstants.*;

/**
 * Utilitaire permettant de renvoyer la bonne exception technique / fonctionnelle en fonction de l'exception renvoyée par EFS
 *
 * @author rjansem
 */
public class TransactionExceptionFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionExceptionFactory.class);

    public static void wrapNOBCException(Throwable err, Object login) {
        NOBCException nobcExceptionData = wrapEfs(err);
        String errorCode = (String) nobcExceptionData.getProperties().get(EFS_CODE);
        Optional<ErrorEnum> errorEnum = ErrorEnum.fromEfsCode(errorCode);
        if (errorEnum.isPresent()) {
            if (errorEnum.get().getPbiCode().equals(CODE_PBI_109) || errorEnum.get().getPbiCode().equals(CODE_PBI_110)
                    || errorEnum.get().getPbiCode().equals(CODE_PBI_104)) {
                LOGGER.info("[ANALYTICS] [{}] [Transaction] [NewTransferError]", login);
            } else {
                LOGGER.info("[ANALYTICS] [{}] [Transaction] [TransactionError]", login);
            }
            throw new NOBCException(EfsCode.fromStatus(errorEnum.get().getHttpStatus().value()))
                    .set(EFS_MESSAGE, errorEnum.get().getPbiLabel()).set(EFS_CODE, errorEnum.get().getPbiCode());
        } else {
            throw new NOBCException("Code erreur " + errorCode + " non défini", TechnicalCode.ILLEGAL_STATE);
        }
    }

    public static void wrapNOBCException(Alarme alarme, Object login) {
        String errorCode = alarme.getCode();
        Optional<ErrorEnum> errorEnum = ErrorEnum.fromEfsCode(errorCode);
        if (errorEnum.isPresent()) {
            if (errorEnum.get().getPbiCode().equals(CODE_PBI_109) || errorEnum.get().getPbiCode().equals(CODE_PBI_110)
                    || errorEnum.get().getPbiCode().equals(CODE_PBI_104)) {
                LOGGER.info("[ANALYTICS] [{}] [Transaction] [NewTransferError]", login);
            } else {
                LOGGER.info("[ANALYTICS] [{}] [Transaction] [TransactionError]", login);
            }
            NOBCException nobcException = new NOBCException(EfsCode.fromStatus(errorEnum.get().getHttpStatus().value()))
                    .set(EFS_MESSAGE, errorEnum.get().getPbiLabel()).set(EFS_CODE, errorEnum.get().getPbiCode());
            throw new CompositeException(nobcException);
        } else {
            throw new NOBCException(alarme.getCode(), TechnicalCode.EFS_BAD_REQUEST);
        }
    }
}
