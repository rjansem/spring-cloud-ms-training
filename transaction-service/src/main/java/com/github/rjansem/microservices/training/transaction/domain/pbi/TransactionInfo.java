package com.github.rjansem.microservices.training.transaction.domain.pbi;

import com.github.rjansem.microservices.training.commons.domain.PbiBean;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Bean représentant les informations d'une transaction
 *
 * @author aazzerrifi
 */
public class TransactionInfo implements PbiBean {

    @NotEmpty
    private String paymentMode;

    private Boolean urgentTransfer;

    private String memo;

    @NotNull
    private BigDecimal instructedAmount;

    @NotEmpty
    private String instructedCurrency;

    @NotEmpty
    private String executionDateTime;

    public TransactionInfo() {
        // for jackson
    }

    public TransactionInfo(String paymentMode, String instructedCurrency, String executionDateTime) {
        this.paymentMode = paymentMode;
        this.instructedCurrency = instructedCurrency;
        this.executionDateTime = executionDateTime;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Boolean getUrgentTransfer() {
        return urgentTransfer;
    }

    public void setUrgentTransfer(Boolean urgentTransfer) {
        this.urgentTransfer = urgentTransfer;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public BigDecimal getInstructedAmount() {
        return instructedAmount;
    }

    public void setInstructedAmount(BigDecimal instructedAmount) {
        this.instructedAmount = instructedAmount;
    }

    public String getInstructedCurrency() {
        return instructedCurrency;
    }

    public void setInstructedCurrency(String instructedCurrency) {
        this.instructedCurrency = instructedCurrency;
    }

    public String getExecutionDateTime() {
        return executionDateTime;
    }

    public void setExecutionDateTime(String executionDateTime) {
        this.executionDateTime = executionDateTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("paymentMode", paymentMode)
                .append("instructedAmount", instructedAmount)
                .append("instructedCurrency", instructedCurrency)
                .toString();
    }
}
