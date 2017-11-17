package com.github.rjansem.microservices.training.exception;

/**
 * Codes d'exception fonctionnels
 *
 * @author jntakpe
 */
public enum FunctionalCode implements ExceptionCode {

    VALIDATION(400),
    NOT_FOUND(404);

    private final int status;

    FunctionalCode(int status) {
        this.status = status;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getStatus() {
        return status;
    }
}
