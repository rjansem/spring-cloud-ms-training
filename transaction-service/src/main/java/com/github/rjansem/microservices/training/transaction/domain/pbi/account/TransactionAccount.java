package com.github.rjansem.microservices.training.transaction.domain.pbi.account;

import com.github.rjansem.microservices.training.commons.domain.PbiBean;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Bean représentant un compte de transaction
 *
 * @author aazzerrifi
 */
public class TransactionAccount implements PbiBean {

    private String id;

    private String type;

    private String typeId;

    private String subtype;

    private String subtypeId;

    private BigDecimal accountBalance;

    private String accountBalanceCurrency;

    private String accountNumber;

    private String iban;

    private String accountLabel;

    private String beneficiaryLastName;

    public TransactionAccount() {
        //for jackson
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getBeneficiaryLastName() {
        return beneficiaryLastName;
    }

    public void setBeneficiaryLastName(String beneficiaryLastName) {
        this.beneficiaryLastName = beneficiaryLastName;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getAccountBalanceCurrency() {
        return accountBalanceCurrency;
    }

    public void setAccountBalanceCurrency(String accountBalanceCurrency) {
        this.accountBalanceCurrency = accountBalanceCurrency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionAccount)) {
            return false;
        }
        TransactionAccount that = (TransactionAccount) o;
        return Objects.equals (id, that.id) &&
                Objects.equals (iban, that.iban);
    }

    @Override
    public int hashCode() {
        return Objects.hash (id, iban);
    }

    @Override
    public String toString() {
        return new ToStringBuilder (this)
                .append ("id", id)
                .append ("iban", iban)
                .toString ( );
    }
}
