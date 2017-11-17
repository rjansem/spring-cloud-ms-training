package com.github.rjansem.microservices.training.account.domain.pbi.portfolioAndLI;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.rjansem.microservices.training.commons.domain.PbiBean;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Objet représentant le detail d'un portefeuilles ou assurance vie (titres et/ou comptes associés)
 *
 * @author mbouhamyd
 */
public class InvestissementDetail implements PbiBean {

    private String accountNumber;

    private String type;

    private String typeId;

    private String subtype;

    private String subtypeId;

    private String currency;

    private String company;

    private String availableBalance;

    private String availableBalanceDate;

    private BigDecimal bookedBalance;

    private String bookedBalanceDate;

    @JsonIgnore
    private String iban;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<AssociatedAccount> associatedAccounts = new HashSet<>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<AssetClass> assetClassIdentification = new HashSet<>();

    public String getAccountNumber() {
        return accountNumber;
    }

    public InvestissementDetail setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public String getType() {
        return type;
    }

    public InvestissementDetail setType(String type) {
        this.type = type;
        return this;
    }

    public String getTypeId() {
        return typeId;
    }

    public InvestissementDetail setTypeId(String typeId) {
        this.typeId = typeId;
        return this;
    }

    public String getSubtype() {
        return subtype;
    }

    public InvestissementDetail setSubtype(String subtype) {
        this.subtype = subtype;
        return this;
    }

    public String getSubtypeId() {
        return subtypeId;
    }

    public InvestissementDetail setSubtypeId(String subtypeId) {
        this.subtypeId = subtypeId;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public InvestissementDetail setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public InvestissementDetail setCompany(String company) {
        this.company = company;
        return this;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public InvestissementDetail setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
        return this;
    }

    public String getAvailableBalanceDate() {
        return availableBalanceDate;
    }

    public InvestissementDetail setAvailableBalanceDate(String availableBalanceDate) {
        this.availableBalanceDate = availableBalanceDate;
        return this;
    }

    public BigDecimal getBookedBalance() {
        return bookedBalance;
    }

    public InvestissementDetail setBookedBalance(BigDecimal bookedBalance) {
        this.bookedBalance = bookedBalance;
        return this;
    }

    public String getBookedBalanceDate() {
        return bookedBalanceDate;
    }

    public InvestissementDetail setBookedBalanceDate(String bookedBalanceDate) {
        this.bookedBalanceDate = bookedBalanceDate;
        return this;
    }

    public String getIban() {
        return iban;
    }

    public InvestissementDetail setIban(String iban) {
        this.iban = iban;
        return this;
    }

    public Set<AssociatedAccount> getAssociatedAccounts() {
        return associatedAccounts;
    }

    public InvestissementDetail setAssociatedAccounts(Set<AssociatedAccount> associatedAccounts) {
        this.associatedAccounts = associatedAccounts;
        return this;
    }

    public Set<AssetClass> getAssetClassIdentification() {
        return assetClassIdentification;
    }

    public InvestissementDetail setAssetClassIdentification(Set<AssetClass> assetClassIdentification) {
        this.assetClassIdentification = assetClassIdentification;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .append("typeId", typeId)
                .append("bookedBalance", bookedBalance)
                .append("associatedAccounts", associatedAccounts)
                .append("assetClassIdentification", assetClassIdentification)
                .toString();
    }
}
