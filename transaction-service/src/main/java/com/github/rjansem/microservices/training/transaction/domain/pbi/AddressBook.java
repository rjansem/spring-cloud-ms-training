package com.github.rjansem.microservices.training.transaction.domain.pbi;

import com.github.rjansem.microservices.training.commons.domain.PbiBean;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.Objects;


/**
 * Bean représentant un beneficiaire
 * @author aazzerrifi
 */
public class AddressBook implements PbiBean, Comparable<AddressBook> {

    private String id;

    private String beneficiaryFirstname;

    private String beneficiaryMiddlename;

    private String beneficiaryLastname;

    private URL photoURL;

    private String active;

    private String email;

    private String phone;

    private String address;

    private String city;

    private String state;

    private Date dateOfBirth;

    private String accountId;

    private String accountNumber;

    private String type;

    private String typeId;

    private String subtype;

    private String subtypeId;

    private String scheme;

    private String iban;

    private String bic;

    private BigDecimal balance;

    private Date balanceDate;

    private String balanceCurrency;

    public AddressBook() {
        //for jackson
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBeneficiaryFirstname() {
        return beneficiaryFirstname;
    }

    public void setBeneficiaryFirstname(String beneficiaryFirstname) {
        this.beneficiaryFirstname = beneficiaryFirstname;
    }

    public String getBeneficiaryMiddlename() {
        return beneficiaryMiddlename;
    }

    public void setBeneficiaryMiddlename(String beneficiaryMiddlename) {
        this.beneficiaryMiddlename = beneficiaryMiddlename;
    }

    public String getBeneficiaryLastname() {
        return beneficiaryLastname;
    }

    public void setBeneficiaryLastname(String beneficiaryLastname) {
        this.beneficiaryLastname = beneficiaryLastname;
    }

    public URL getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(URL photoURL) {
        this.photoURL = photoURL;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(Date balanceDate) {
        this.balanceDate = balanceDate;
    }

    public String getBalanceCurrency() {
        return balanceCurrency;
    }

    public void setBalanceCurrency(String balanceCurrency) {
        this.balanceCurrency = balanceCurrency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddressBook)) {
            return false;
        }
        AddressBook that = (AddressBook) o;
        return Objects.equals (id, that.id) &&
                Objects.equals (iban, that.iban);
    }

    @Override
    public int hashCode() {
        return Objects.hash (id, iban);
    }

    @Override
    public int compareTo(AddressBook o) {
        return this.getType().compareTo(o.getType());
    }
    @Override
    public String toString() {
        return new ToStringBuilder (this)
                .append ("id", id)
                .append ("accountId", accountId)
                .append ("scheme", scheme)
                .append ("iban", iban)
                .toString ( );
    }


}

