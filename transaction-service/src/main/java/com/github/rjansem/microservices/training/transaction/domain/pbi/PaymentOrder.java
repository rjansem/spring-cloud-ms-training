package com.github.rjansem.microservices.training.transaction.domain.pbi;

import com.github.rjansem.microservices.training.transaction.domain.pbi.account.TransactionAccount;
import com.github.rjansem.microservices.training.commons.domain.PbiBean;
import com.github.rjansem.microservices.training.transaction.domain.pbi.account.TransactionAccount;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Bean représentant un ordre de paiement
 *
 * @author aazzerrifi
 */
public class PaymentOrder implements PbiBean, Serializable {

    private String id;

    private String status;

    @NotNull
    private TransactionAccount debitAccount;

    @NotNull
    private TransactionAccount creditAccount;

    @NotNull
    private TransactionInfo transactionInfo;

    public PaymentOrder() {
        // for jackson
    }

    public PaymentOrder(String id, String status, TransactionAccount debitAccount, TransactionAccount creditAccount, TransactionInfo transactionInfo) {
        this.id = id;
        this.status = status;
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
        this.transactionInfo = transactionInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TransactionAccount getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(TransactionAccount debitAccount) {
        this.debitAccount = debitAccount;
    }

    public TransactionAccount getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(TransactionAccount creditAccount) {
        this.creditAccount = creditAccount;
    }

    public TransactionInfo getTransactionInfo() {
        return transactionInfo;
    }

    public void setTransactionInfo(TransactionInfo transactionInfo) {
        this.transactionInfo = transactionInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentOrder)) {
            return false;
        }
        PaymentOrder that = (PaymentOrder) o;
        return Objects.equals (id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash (id);
    }

    @Override
    public String toString() {
        return new ToStringBuilder (this)
                .append ("id", id)
                .append ("status", status)
                .append ("transactionInfo", transactionInfo)
                .toString ( );
    }

    public boolean checkInternal() {
        return creditAccount.getAccountNumber() == null;
    }
}
