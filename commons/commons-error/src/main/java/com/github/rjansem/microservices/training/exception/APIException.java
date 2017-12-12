package com.github.rjansem.microservices.training.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

/**
 * Exception générale de l'application
 *
 * @author rjansem
 */
@JsonIgnoreProperties({"stackTrace", "localizedMessage", "suppressed"})
public class APIException extends RuntimeException {

    public static final String EFS_CODE = "efsCode";

    public static final String EFS_MESSAGE = "efsMessage";

    private static final Logger LOGGER = LoggerFactory.getLogger(APIException.class);

    private final ExceptionCode exceptionCode;

    private final Map<String, Object> properties = new TreeMap<>();

    public APIException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public APIException(String message, ExceptionCode exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public APIException(Throwable cause, ExceptionCode exceptionCode) {
        super(cause);
        this.exceptionCode = exceptionCode;
    }

    public APIException(String message, Throwable cause, ExceptionCode exceptionCode) {
        super(message, cause);
        this.exceptionCode = exceptionCode;
    }

    /**
     * Transforme une exception existante en {@link APIException}
     *
     * @param exception exception à transformer
     * @return exception correspondante
     */
    public static APIException wrap(Throwable exception) {
        return wrap(exception, null);
    }

    /**
     * Transforme une exception existante en {@link APIException}
     *
     * @param exception     exception à transformer
     * @param exceptionCode code à attribuer à l'exception
     * @return exception correspondante
     */
    public static APIException wrap(Throwable exception, ExceptionCode exceptionCode) {
        if (exception instanceof APIException) {
            APIException se = (APIException) exception;
            if (exceptionCode != null && exceptionCode != se.getExceptionCode()) {
                return new APIException(exception.getMessage(), exception, exceptionCode);
            }
            return se;
        } else {
            return new APIException(exception.getMessage(), exception, exceptionCode);
        }
    }

    /**
     * Transforme une exception existante provenant d'EFS en {@link APIException}
     *
     * @param exception exception à transformer
     * @return exception correspondante
     */
    public static APIException wrapEfs(Throwable exception) {
        if (exception instanceof HystrixRuntimeException) {
            HystrixRuntimeException hystrixException = (HystrixRuntimeException) exception;
            Throwable cause = hystrixException.getCause();
            if (cause instanceof FeignException) {
                FeignException feignException = (FeignException) cause;
                APIException apiException = wrap(exception, EfsCode.fromStatus(feignException.status()));
                if (cause instanceof EfsFeignException) {
                    EfsFeignException efsFeignException = (EfsFeignException) cause;
                    EfsExceptionBean efsException = efsFeignException.getEfsException();
                    apiException
                            .set(EFS_CODE, efsException.getCode())
                            .set(EFS_MESSAGE, efsException.getMessage());
                }
                return apiException;
            }
        }
        return wrap(exception, EfsCode.fromStatus(500));
    }

    /**
     * Log et lance une exception {@link APIException} lors d'une erreur EFS
     *
     * @param exception exception lancée par EFS
     */
    public static void throwEfsError(String message, Throwable exception) {
        LOGGER.warn(message, exception);
        throw wrapEfs(exception);
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String name) {
        return (T) properties.get(name);
    }

    public APIException set(String name, Object value) {
        properties.put(name, value);
        return this;
    }

    @Override
    public void printStackTrace(PrintStream s) {
        printStackTrace(new PrintWriter(s));
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        s.println(this);
        s.println("\t-------------------------------");
        if (exceptionCode != null) {
            s.println("\t" + exceptionCode + ":" + exceptionCode.getClass().getName());
        }
        for (String key : properties.keySet()) {
            s.println("\t" + key + "=[" + properties.get(key) + "]");
        }
        s.println("\t-------------------------------");
        StackTraceElement[] trace = getStackTrace();
        for (StackTraceElement aTrace : trace) {
            s.println("\tat " + aTrace);
        }
        Throwable ourCause = getCause();
        if (ourCause != null) {
            ourCause.printStackTrace(s);
        }
        s.flush();
    }

}
