package com.github.rjansem.microservices.training.exception;

/**
 * Codes d'exception techniques
 *
 * @author rjansem
 */
public enum TechnicalCode implements ExceptionCode {

    EFS_BAD_REQUEST(400),
    EFS_UNAUTHORIZED(401),
    EFS_FORBIDDEN(403),
    EFS_NOT_FOUND(404),
    EFS_ERROR_INTERNAL(500),
    ILLEGAL_STATE(500);

    private final int status;

    TechnicalCode(int status) {
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
