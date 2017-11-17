package com.github.rjansem.microservices.training.exception;

import java.util.Arrays;

/**
 * Codes d'exceptions d'EFS
 *
 * @author jntakpe
 */
public enum EfsCode implements ExceptionCode {

    BAD_REQUEST(400),
    OK(200),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    INTERNAL(500);

    private final int status;

    EfsCode(int status) {
        this.status = status;
    }

    public static EfsCode fromStatus(int status) {
        return Arrays.stream(EfsCode.values()).filter(c -> c.getStatus() == status).findAny().orElse(INTERNAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getStatus() {
        return status;
    }
}
