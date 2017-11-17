package com.github.rjansem.microservices.training.transaction.domain.pbi.account;

import com.github.rjansem.microservices.training.commons.domain.PbiBean;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Bean représentant un compte emetteur
 *
 * @author mbouhamyd
 */
public class Account implements PbiBean {

    @NotEmpty
    private String type;

    @NotEmpty
    private String typeId;

    private String subtype;

    private String subtypeId;

    @NotEmpty
    private String id;

    private String iban;

    private String accountNumber;

    private String accountLabel;

    private BigDecimal balance;

    private String balanceDate;

    private String currency;

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

    public void setId(String id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountLabel() {
        return accountLabel;
    }

    public void setAccountLabel(String accountLabel) {
        this.accountLabel = accountLabel;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(String balanceDate) {
        this.balanceDate = balanceDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    //FIXME [Code review Jocelyn] : Non le equals est juste faux. Le numéro de compte ou l'id devrait permettre de différencier les comptes
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account that = (Account) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(accountNumber, that.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, id);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("accountNumber", accountNumber)
                .append("accountLabel", accountLabel)
                .toString();
    }
}
