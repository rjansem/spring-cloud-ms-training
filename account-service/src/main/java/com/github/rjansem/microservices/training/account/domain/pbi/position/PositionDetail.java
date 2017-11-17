package com.github.rjansem.microservices.training.account.domain.pbi.position;

import com.github.rjansem.microservices.training.commons.domain.PbiBean;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Objet représentant un position detail
 *
 * @author mbouhamyd
 */
public class PositionDetail implements PbiBean {

    private String accountNumber;

    private String subtype;

    private String subtypeId;

    private String company;

    private String positionValue;

    private String positionValueCode;

    private BigDecimal amount;

    private BigDecimal returnOnInvestment;

    private String returnOnInvestmentCurrency;

    private BigDecimal rate;

    private String rateCurrency;

    private BigDecimal estimation;

    private String estimationCurrency;

    private BigDecimal percentage;

    private String date;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPositionValue() {
        return positionValue;
    }

    public void setPositionValue(String positionValue) {
        this.positionValue = positionValue;
    }

    public String getPositionValueCode() {
        return positionValueCode;
    }

    public void setPositionValueCode(String positionValueCode) {
        this.positionValueCode = positionValueCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getReturnOnInvestment() {
        return returnOnInvestment;
    }

    public void setReturnOnInvestment(BigDecimal returnOnInvestment) {
        this.returnOnInvestment = returnOnInvestment;
    }

    public String getReturnOnInvestmentCurrency() {
        return returnOnInvestmentCurrency;
    }

    public void setReturnOnInvestmentCurrency(String returnOnInvestmentCurrency) {
        this.returnOnInvestmentCurrency = returnOnInvestmentCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getRateCurrency() {
        return rateCurrency;
    }

    public void setRateCurrency(String rateCurrency) {
        this.rateCurrency = rateCurrency;
    }

    public BigDecimal getEstimation() {
        return estimation;
    }

    public void setEstimation(BigDecimal estimation) {
        this.estimation = estimation;
    }

    public String getEstimationCurrency() {
        return estimationCurrency;
    }

    public void setEstimationCurrency(String estimationCurrency) {
        this.estimationCurrency = estimationCurrency;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("accountNumber", accountNumber)
                .append("percentage", percentage)
                .append("date", date)
                .toString();
    }
}
