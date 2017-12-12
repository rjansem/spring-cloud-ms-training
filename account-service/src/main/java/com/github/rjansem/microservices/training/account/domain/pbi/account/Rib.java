package com.github.rjansem.microservices.training.account.domain.pbi.account;

import com.github.rjansem.microservices.training.commons.domain.IdentifiableDomain;
import com.github.rjansem.microservices.training.commons.domain.PbiBean;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Objects;

/**
 * Bean représentant le RIB d'un compte d'un client
 *
 * @author rjansem
 */
public class Rib implements PbiBean, IdentifiableDomain {

    @NotEmpty
    private String type;

    @NotEmpty
    private String typeId;

    private String subtype;

    private String subtypeId;

    @NotEmpty
    private String id;

    @NotEmpty
    private String accountNumber;

    @NotEmpty
    private String iban;

    @NotEmpty
    private String bic;

    @NotEmpty
    private String bankName;

    @NotEmpty
    private String bankAddress;

    private String clientFirstName;

    private String clientMiddleName;

    @NotEmpty
    private String clientLastName;

    public Rib() {
    }

    public Rib(String type, String typeId, String id, String accountNumber, String iban, String bic, String bankName, String bankAddress, String clientLastName) {
        this.type = type;
        this.typeId = typeId;
        this.id = id;
        this.accountNumber = accountNumber;
        this.iban = iban;
        this.bic = bic;
        this.bankName = bankName;
        this.bankAddress = bankAddress;
        this.clientLastName = clientLastName;
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

    public Rib setId(String id) {
        this.id = id;
        return this;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientMiddleName() {
        return clientMiddleName;
    }

    public void setClientMiddleName(String clientMiddleName) {
        this.clientMiddleName = clientMiddleName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rib rib = (Rib) o;
        return Objects.equals(iban, rib.iban);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .append("typeId", typeId)
                .append("id", id)
                .append("accountNumber", accountNumber)
                .append("iban", iban)
                .append("bic", bic)
                .append("bankName", bankName)
                .append("bankAddress", bankAddress)
                .append("clientLastName", clientLastName)
                .toString();
    }
}
