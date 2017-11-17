package com.github.rjansem.microservices.training.account.domain.pbi.cartebancaire;

import com.github.rjansem.microservices.training.commons.domain.PbiBean;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Bean représentant la carte bancaire
 *
 * @author mbouhamyd
 */
public class CreditCard implements PbiBean {

    @NotNull
    private String creditcardId;

    private String cardNumber;

    private String cardType;

    private String cardName;

    private BigDecimal cardBalance;

    private String cardBalanceDate;

    private String cardCurrency;

    private String date;

    public CreditCard() {
    }

    public CreditCard(String creditCardId, String cardNumber, String cardType, String name, BigDecimal cardBalance, String cardBalanceDate,
                      String cardCurrency, String date) {
        this.creditcardId = creditCardId;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.cardName = name;
        this.cardBalance = cardBalance;
        this.cardBalanceDate = cardBalanceDate;
        this.cardCurrency = cardCurrency;
        this.date = date;
    }

    public String getCreditcardId() {
        return creditcardId;
    }

    public CreditCard setCreditcardId(String creditcardId) {
        this.creditcardId = creditcardId;
        return this;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public CreditCard setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public String getCardType() {
        return cardType;
    }

    public CreditCard setCardType(String cardType) {
        this.cardType = cardType;
        return this;
    }

    public String getCardName() {
        return cardName;
    }

    public CreditCard setCardName(String cardName) {
        this.cardName = cardName;
        return this;
    }

    public BigDecimal getCardBalance() {
        return cardBalance;
    }

    public CreditCard setCardBalance(BigDecimal cardBalance) {
        this.cardBalance = cardBalance;
        return this;
    }

    public String getCardBalanceDate() {
        return cardBalanceDate;
    }

    public CreditCard setCardBalanceDate(String cardBalanceDate) {
        this.cardBalanceDate = cardBalanceDate;
        return this;
    }

    public String getCardCurrency() {
        return cardCurrency;
    }

    public CreditCard setCardCurrency(String cardCurrency) {
        this.cardCurrency = cardCurrency;
        return this;
    }

    public String getDate() {
        return date;
    }

    public CreditCard setDate(String date) {
        this.date = date;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreditCard)) {
            return false;
        }
        CreditCard that = (CreditCard) o;
        return Objects.equals(creditcardId, that.creditcardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creditcardId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("cardNumber", cardNumber)
                .append("cardType", cardType)
                .append("cardName", cardName)
                .toString();
    }


}
