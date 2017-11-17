package com.github.rjansem.microservices.training.account.domain.pbi.portfolioAndLI;

import com.github.rjansem.microservices.training.commons.domain.IdentifiableDomain;
import com.github.rjansem.microservices.training.commons.domain.PbiBean;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Objet représentant un compte associé à un portefeuilles
 *
 * @author mbouhamyd
 */

public class AssociatedAccount implements PbiBean, IdentifiableDomain {

    private String type;

    private String typeId;

    private String subtype;

    private String subtypeId;

    private String accountNumber;

    private String id;

    private BigDecimal bookedBalance;

    private String bookedBalanceDate;

    private String accountCurrency;

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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getId() {
        return id;
    }

    public AssociatedAccount setId(String id) {
        this.id = id;
        return this;
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

    public void setBookedBalanceDate(String bookBalanceDate) {
        this.bookedBalanceDate = bookBalanceDate;
    }

    public String getAccountCurrency() {
        return accountCurrency;
    }

    public void setAccountCurrency(String accountCurrency) {
        this.accountCurrency = accountCurrency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssociatedAccount)) {
            return false;
        }
        AssociatedAccount that = (AssociatedAccount) o;
        return Objects.equals(accountNumber, that.accountNumber) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, id);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .append("accountNumber", accountNumber)
                .append("bookedBalance", bookedBalance)
                .append("accountCurrency", accountCurrency)
                .toString();
    }
}
