package com.github.rjansem.microservices.training.transaction.domain.pbi.transaction;

import com.github.rjansem.microservices.training.commons.domain.PbiBean;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Objects;

/**
 * Bean représentant un ordre de paiement
 *
 * @author rjansem
 */
public class Payment implements PbiBean {

    @NotEmpty
    private String transactionId;

    @NotEmpty
    private String status;

    @NotEmpty
    private String transactionDateTime;

    public Payment() {
        // for jackson
    }

    public Payment(String transactionId, String status, String transactionDateTime) {
        this.transactionId = transactionId;
        this.status = status;
        this.transactionDateTime = transactionDateTime;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(String transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        Payment payment = (Payment) o;
        return Objects.equals (transactionId, payment.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash (transactionId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder (this)
                .append ("transactionId", transactionId)
                .append ("status", status)
                .append ("transactionDateTime", transactionDateTime)
                .toString ( );
    }
}
