package com.github.rjansem.microservices.training.transaction.domain.pbi.transaction;


import com.github.rjansem.microservices.training.commons.domain.PbiBean;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Bean représentant une transaction
 *
 * @author mbouhamyd
 */
public class Transaction implements PbiBean, Serializable {
    private String id;

    private String status;

    private String accountId;

    private String accountNumber;

    private String type;

    private String typeId;

    private String subtype;

    private String subtypeId;

    private String clientName;

    private String initiationDate;

    private String bookingDateTime;

    private String counterpartyAccountNumber;

    private String counterpartyIban;

    private String counterpartyId;

    private String counterpartyFirstName;

    private String counterpartyMiddleName;

    private String counterpartyLastName;

    private BigDecimal instructedAmount;

    private String instructedCurrency;

    private String transactionType;

    private String description;

    private String creditDebitIndicator;

    private String paymentMode;

    private String counterpartyAccountType;

    private String counterpartyAccountTypeId;

    private String counterpartySubtype;

    private String counterpartySubtypeId;

    public Transaction() {
        //for jackson
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getSubtypeId() {
        return subtypeId;
    }

    public void setSubtypeId(String subtypeId) {
        this.subtypeId = subtypeId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getInitiationDate() {
        return initiationDate;
    }

    public void setInitiationDate(String initiationDate) {
        this.initiationDate = initiationDate;
    }

    public String getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(String bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    public String getCounterpartyAccountNumber() {
        return counterpartyAccountNumber;
    }

    public void setCounterpartyAccountNumber(String counterpartyAccountNumber) {
        this.counterpartyAccountNumber = counterpartyAccountNumber;
    }

    public String getCounterpartyIban() {
        return counterpartyIban;
    }

    public void setCounterpartyIban(String counterpartyIban) {
        this.counterpartyIban = counterpartyIban;
    }

    public String getCounterpartyId() {
        return counterpartyId;
    }

    public void setCounterpartyId(String counterpartyId) {
        this.counterpartyId = counterpartyId;
    }

    public String getCounterpartyFirstName() {
        return counterpartyFirstName;
    }

    public void setCounterpartyFirstName(String counterpartyFirstName) {
        this.counterpartyFirstName = counterpartyFirstName;
    }

    public String getCounterpartyMiddleName() {
        return counterpartyMiddleName;
    }

    public void setCounterpartyMiddleName(String counterpartyMiddleName) {
        this.counterpartyMiddleName = counterpartyMiddleName;
    }

    public String getCounterpartyLastName() {
        return counterpartyLastName;
    }

    public void setCounterpartyLastName(String counterpartyLastName) {
        this.counterpartyLastName = counterpartyLastName;
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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreditDebitIndicator() {
        return creditDebitIndicator;
    }

    public void setCreditDebitIndicator(String creditDebitIndicator) {
        this.creditDebitIndicator = creditDebitIndicator;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getCounterpartyAccountType() {
        return counterpartyAccountType;
    }

    public void setCounterpartyAccountType(String counterpartyAccountType) {
        this.counterpartyAccountType = counterpartyAccountType;
    }

    public String getCounterpartyAccountTypeId() {
        return counterpartyAccountTypeId;
    }

    public void setCounterpartyAccountTypeId(String counterpartyAccountTypeId) {
        this.counterpartyAccountTypeId = counterpartyAccountTypeId;
    }

    public String getCounterpartySubtype() {
        return counterpartySubtype;
    }

    public void setCounterpartySubtype(String counterpartySubtype) {
        this.counterpartySubtype = counterpartySubtype;
    }

    public String getCounterpartySubtypeId() {
        return counterpartySubtypeId;
    }

    public void setCounterpartySubtypeId(String counterpartySubtypeId) {
        this.counterpartySubtypeId = counterpartySubtypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("accountId", accountId)
                .toString();
    }


}
