package com.github.rjansem.microservices.training.profile.domain.pib;

import com.github.rjansem.microservices.training.commons.domain.IdentifiableDomain;
import com.github.rjansem.microservices.training.commons.domain.PbiBean;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Objects;

/**
 * Bean représentant le compte d'un client
 *
 * @author aazzerrifi
 * @author jntakpe
 */
public class Account implements IdentifiableDomain, PbiBean {

    @NotEmpty
    private String id;

    @NotEmpty
    private String accountNumber;

    @NotEmpty
    private String type;

    @NotEmpty
    private String typeId;

    private String subtype;

    private String subtypeId;

    private String iban;

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Account() {
        // for jakson
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Account setId(String id) {
        this.id = id;
        return this;
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

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("accountNumber", accountNumber)
                .append("type", type)
                .append("subtype", subtype)
                .toString();
    }
}
