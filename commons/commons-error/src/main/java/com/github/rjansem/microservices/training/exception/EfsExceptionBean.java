package com.github.rjansem.microservices.training.exception;

/**
 * Bean mappant une exception provenant d'EFS
 *
 * @author jntakpe
 */
public class EfsExceptionBean {

    private String[] stackTrace;

    private String status;

    private String message;

    private String[] errors;

    private String localizedMessage;

    private String code;

    private String[] suppressed;

    public String[] getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String[] stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getErrors() {
        return errors;
    }

    public void setErrors(String[] errors) {
        this.errors = errors;
    }

    public String getLocalizedMessage() {
        return localizedMessage;
    }

    public void setLocalizedMessage(String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String[] getSuppressed() {
        return suppressed;
    }

    public void setSuppressed(String[] suppressed) {
        this.suppressed = suppressed;
    }
}
