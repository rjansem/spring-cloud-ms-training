package com.github.rjansem.microservices.training.account.domain.pbi.account;

import com.github.rjansem.microservices.training.account.domain.pbi.cartebancaire.CreditCard;
import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType;
import com.github.rjansem.microservices.training.commons.domain.IdentifiableDomain;
import com.github.rjansem.microservices.training.commons.domain.PbiBean;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Bean représentant le retrieve balance
 *
 * @author mbouhmyd
 */
public class RetrieveBalance implements PbiBean, IdentifiableDomain, Comparable<RetrieveBalance> {

    @NotEmpty
    private String type;

    @NotEmpty
    private String typeId;

    @NotEmpty
    private String subtype;

    @NotEmpty
    private String subtypeId;

    @NotEmpty
    private String id;

    @NotEmpty
    private String accountNumber;

    @NotEmpty
    private String iban;

    @NotEmpty
    private BigDecimal bookedBalance;

    @NotEmpty
    private String bookedBalanceDate;

    private String bookedBalanceCurrency;

    private BigDecimal countervalueBalance;

    private String countervalueDate;

    private String countervalueCurrency;

    private BigDecimal countervalueRate;

    private BigDecimal creditcardTotalBalance;

    @NotEmpty
    private String creditcardTotalBalanceDate;

    private String creditcardTotalCurrency;

    private Set<CreditCard> creditcards = new HashSet<>();

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

    public String getId() {
        return id;
    }

    public RetrieveBalance setId(String id) {
        this.id = id;
        return this;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAcountNumber(String acountNumber) {
        this.accountNumber = acountNumber;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public BigDecimal getBookedBalance() {
        return bookedBalance;
    }

    public void setBookedBalance(BigDecimal bookedBalance) {
        this.bookedBalance = bookedBalance;
    }

    public String getBookedBalanceDate() {
        return bookedBalanceDate;
    }

    public void setBookedBalanceDate(String bookedBalanceDate) {
        this.bookedBalanceDate = bookedBalanceDate;
    }

    public String getBookedBalanceCurrency() {
        return bookedBalanceCurrency;
    }

    public void setBookedBalanceCurrency(String bookedBalanceCurrency) {
        this.bookedBalanceCurrency = bookedBalanceCurrency;
    }

    public BigDecimal getCountervalueBalance() {
        return countervalueBalance;
    }

    public void setCountervalueBalance(BigDecimal countervalueBalance) {
        this.countervalueBalance = countervalueBalance;
    }

    public String getCountervalueDate() {
        return countervalueDate;
    }

    public void setCountervalueDate(String countervalueDate) {
        this.countervalueDate = countervalueDate;
    }

    public String getCountervalueCurrency() {
        return countervalueCurrency;
    }

    public void setCountervalueCurrency(String countervalueCurrency) {
        this.countervalueCurrency = countervalueCurrency;
    }

    public BigDecimal getCountervalueRate() {
        return countervalueRate;
    }

    public void setCountervalueRate(BigDecimal countervalueRate) {
        this.countervalueRate = countervalueRate;
    }

    public BigDecimal getCreditcardTotalBalance() {
        return creditcardTotalBalance;
    }

    public void setCreditcardTotalBalance(BigDecimal creditcardTotalBalance) {
        this.creditcardTotalBalance = creditcardTotalBalance;
    }

    public String getCreditcardTotalBalanceDate() {
        return creditcardTotalBalanceDate;
    }

    public void setCreditcardTotalBalanceDate(String creditcardTotalBalanceDate) {
        this.creditcardTotalBalanceDate = creditcardTotalBalanceDate;
    }

    public String getCreditcardTotalCurrency() {
        return creditcardTotalCurrency;
    }

    public void setCreditcardTotalCurrency(String creditcardTotalCurrency) {
        this.creditcardTotalCurrency = creditcardTotalCurrency;
    }

    public Set<CreditCard> getCreditcards() {
        return creditcards;
    }

    public RetrieveBalance setCreditCards(Set<CreditCard> creditCards) {
        this.creditcards = creditCards;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RetrieveBalance)) {
            return false;
        }
        RetrieveBalance that = (RetrieveBalance) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(accountNumber, that.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .append("id", id)
                .append("accountNumber", accountNumber)
                .append("bookedBalance", bookedBalance)
                .append("bookedBalanceDate", bookedBalanceDate)
                .toString();
    }

    @Override
    public int compareTo(RetrieveBalance other) {
        return CompteType.fromId(this.getTypeId()).getLibelle().compareTo(CompteType.fromId(other.getTypeId()).getLibelle());
    }
}
