package com.github.rjansem.microservices.training.account.domain.pbi.operation;

import com.github.rjansem.microservices.training.commons.domain.IdentifiableDomain;
import com.github.rjansem.microservices.training.commons.domain.PbiBean;
import com.github.rjansem.microservices.training.commons.domain.utils.DateUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Objet représentant une opération
 *
 * @author rjansem
 * @author mbouhamyd
 */
public class Operation implements PbiBean, Comparable<Operation>, IdentifiableDomain {

    private String id;

    private String status;

    private String accountId;

    private String creditcardId;

    private String creditcardAccountNumber;

    private String bookingDateTime;

    private String operationDateTime;

    private BigDecimal instructedAmount;

    private String instructedCurrency;

    private BigDecimal transactionAmount;

    private String transactionCurrency;

    private String merchant;

    private String city;

    private String creditDebitIndicator;

    private String paymentMode;

    private String informationFirst;

    private String informationSecond;

    public String getId() {
        return id;
    }

    public Operation setId(String id) {
        this.id = id;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Operation setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getAccountId() {
        return accountId;
    }

    public Operation setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public String getCreditcardId() {
        return creditcardId;
    }

    public Operation setCreditcardId(String creditcardId) {
        this.creditcardId = creditcardId;
        return this;
    }

    public String getCreditcardAccountNumber() {
        return creditcardAccountNumber;
    }

    public Operation setCreditcardAccountNumber(String creditcardAccountNumber) {
        this.creditcardAccountNumber = creditcardAccountNumber;
        return this;
    }

    public String getBookingDateTime() {
        return bookingDateTime;
    }

    public Operation setBookingDateTime(String bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
        return this;
    }

    public String getOperationDateTime() {
        return operationDateTime;
    }

    public Operation setOperationDateTime(String operationDateTime) {
        this.operationDateTime = operationDateTime;
        return this;
    }

    public BigDecimal getInstructedAmount() {
        return instructedAmount;
    }

    public Operation setInstructedAmount(BigDecimal instructedAmount) {
        this.instructedAmount = instructedAmount;
        return this;
    }

    public String getInstructedCurrency() {
        return instructedCurrency;
    }

    public Operation setInstructedCurrency(String instructedCurrency) {
        this.instructedCurrency = instructedCurrency;
        return this;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public Operation setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
        return this;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public Operation setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
        return this;
    }

    public String getMerchant() {
        return merchant;
    }

    public Operation setMerchant(String merchant) {
        this.merchant = merchant;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Operation setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCreditDebitIndicator() {
        return creditDebitIndicator;
    }

    public Operation setCreditDebitIndicator(String creditDebitIndicator) {
        this.creditDebitIndicator = creditDebitIndicator;
        return this;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public Operation setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
        return this;
    }

    public String getInformationFirst() {
        return informationFirst;
    }

    public Operation setInformationFirst(String informationFirst) {
        this.informationFirst = informationFirst;
        return this;
    }

    public String getInformationSecond() {
        return informationSecond;
    }

    public Operation setInformationSecond(String informationSecond) {
        this.informationSecond = informationSecond;
        return this;
    }

    @Override
    public int compareTo(Operation other) {
        LocalDateTime current = LocalDateTime.parse(this.getBookingDateTime(), DateTimeFormatter.ofPattern(DateUtils.PIB_DATE_FORMAT));
        LocalDateTime to = LocalDateTime.parse(other.getBookingDateTime(), DateTimeFormatter.ofPattern(DateUtils.PIB_DATE_FORMAT));
        int compareDate = to.compareTo(current);
        LocalDateTime operationcurrent = LocalDateTime.parse(this.getOperationDateTime(), DateTimeFormatter.ofPattern(DateUtils.PIB_DATE_FORMAT));
        LocalDateTime operationto = LocalDateTime.parse(other.getOperationDateTime(), DateTimeFormatter.ofPattern(DateUtils.PIB_DATE_FORMAT));
        int compareoperationDate = operationto.compareTo(operationcurrent);
        String libelleCurrent = this.getMerchant() != null ? this.getMerchant() : this.getInformationFirst();
        String libelleto = other.getMerchant() != null ? other.getMerchant() : this.getInformationFirst();
        if (compareDate == 0) {
            if (compareoperationDate == 0) {
                int comparelibelle = libelleCurrent.compareTo(libelleto);
                if (comparelibelle == 0) {
                    return this.getTransactionAmount().compareTo(other.getTransactionAmount());
                } else
                    return comparelibelle;
            } else {
                return compareoperationDate;
            }
        } else {
            return compareDate;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("accountId", accountId)
                .append("operationDateTime", operationDateTime)
                .append("transactionAmount", transactionAmount)
                .toString();
    }

}
