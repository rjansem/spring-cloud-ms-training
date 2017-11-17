package com.github.rjansem.microservices.training.account.domain.pbi.portfolioAndLI;


import com.github.rjansem.microservices.training.commons.domain.PbiBean;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Objet représentant un titre d'un portefeuilles ou assurance vie
 *
 * @author mbouhamyd
 */
public class Position implements PbiBean {

    private String positionName;

    private BigDecimal positionBalance;

    private String positionCurrency;

    private String positionId;

    private BigDecimal percentage;

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public BigDecimal getPositionBalance() {
        return positionBalance;
    }

    public void setPositionBalance(BigDecimal positionBalance) {
        this.positionBalance = positionBalance;
    }

    public String getPositionCurrency() {
        return positionCurrency;
    }

    public void setPositionCurrency(String positionCurrency) {
        this.positionCurrency = positionCurrency;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("positionName", positionName)
                .append("positionBalance", positionBalance)
                .append("positionCurrency", positionCurrency)
                .append("positionId", positionId)
                .append("percentage", percentage)
                .toString();
    }
}
