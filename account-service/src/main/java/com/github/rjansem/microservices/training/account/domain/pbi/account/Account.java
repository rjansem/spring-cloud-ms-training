package com.github.rjansem.microservices.training.account.domain.pbi.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType;
import com.github.rjansem.microservices.training.commons.domain.IdentifiableDomain;
import com.github.rjansem.microservices.training.commons.domain.PbiBean;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Bean représentant le compte d'un client
 *
 * @author aazzerrifi
 * @author rjansem
 */
public class Account implements IdentifiableDomain, PbiBean, Comparable<Account> {

    @NotEmpty
    private String id;

    @NotEmpty
    private String accountNumber;

    @NotEmpty
    private String currency;

    @NotEmpty
    private String type;

    @NotEmpty
    private String typeId;

    private String subtype;

    private String subtypeId;

    private String company;

    private BigDecimal availableBalance;

    private LocalDate availableBalanceDate;

    private BigDecimal bookedBalance;

    private String bookedBalanceDate;

    private String bookedBalanceCurrency;

    private BigDecimal countervalueBalance;

    private String countervalueCurrency;

    private BigDecimal countervalueRate;

    private LocalDateTime countervalueDate;

    private LocalDate date;

    private BigDecimal nextWriteOffAmount;

    private String nextWriteOffCurrency;

    private Integer creditcardCount;

    private BigDecimal creditcardsTotalBalance;

    private String creditcardsTotalCurrency;

    private String iban;

    @JsonIgnore
    private String creditcardsTotalDate;

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

    public Account setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public Account setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getType() {
        return type;
    }

    public Account setType(String type) {
        this.type = type;
        return this;
    }

    public String getTypeId() {
        return typeId;
    }

    public Account setTypeId(String typeId) {
        this.typeId = typeId;
        return this;
    }

    public String getSubtype() {
        return subtype;
    }

    public Account setSubtype(String subtype) {
        this.subtype = subtype;
        return this;
    }

    public String getSubtypeId() {
        return subtypeId;
    }

    public Account setSubtypeId(String subtypeId) {
        this.subtypeId = subtypeId;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public Account setCompany(String company) {
        this.company = company;
        return this;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public Account setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
        return this;
    }

    public LocalDate getAvailableBalanceDate() {
        return availableBalanceDate;
    }

    public Account setAvailableBalanceDate(LocalDate availableBalanceDate) {
        this.availableBalanceDate = availableBalanceDate;
        return this;
    }

    public BigDecimal getBookedBalance() {
        return bookedBalance;
    }

    public Account setBookedBalance(BigDecimal bookedBalance) {
        this.bookedBalance = bookedBalance;
        return this;
    }

    public String getBookedBalanceDate() {
        return bookedBalanceDate;
    }

    public Account setBookedBalanceDate(String bookedBalanceDate) {
        this.bookedBalanceDate = bookedBalanceDate;
        return this;
    }

    public String getBookedBalanceCurrency() {
        return bookedBalanceCurrency;
    }

    public Account setBookedBalanceCurrency(String bookedBalanceCurrency) {
        this.bookedBalanceCurrency = bookedBalanceCurrency;
        return this;
    }

    public BigDecimal getCountervalueBalance() {
        return countervalueBalance;
    }

    public Account setCountervalueBalance(BigDecimal countervalueBalance) {
        this.countervalueBalance = countervalueBalance;
        return this;
    }

    public String getCountervalueCurrency() {
        return countervalueCurrency;
    }

    public Account setCountervalueCurrency(String countervalueCurrency) {
        this.countervalueCurrency = countervalueCurrency;
        return this;
    }

    public BigDecimal getCountervalueRate() {
        return countervalueRate;
    }

    public Account setCountervalueRate(BigDecimal countervalueRate) {
        this.countervalueRate = countervalueRate;
        return this;
    }

    public LocalDateTime getCountervalueDate() {
        return countervalueDate;
    }

    public Account setCountervalueDate(LocalDateTime countervalueDate) {
        this.countervalueDate = countervalueDate;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public Account setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public BigDecimal getNextWriteOffAmount() {
        return nextWriteOffAmount;
    }

    public Account setNextWriteOffAmount(BigDecimal nextWriteOffAmount) {
        this.nextWriteOffAmount = nextWriteOffAmount;
        return this;
    }

    public String getNextWriteOffCurrency() {
        return nextWriteOffCurrency;
    }

    public Account setNextWriteOffCurrency(String nextWriteOffCurrency) {
        this.nextWriteOffCurrency = nextWriteOffCurrency;
        return this;
    }

    public Integer getCreditcardCount() {
        return creditcardCount;
    }

    public Account setCreditcardCount(Integer creditcardCount) {
        this.creditcardCount = creditcardCount;
        return this;
    }

    public BigDecimal getCreditcardsTotalBalance() {
        return creditcardsTotalBalance;
    }

    public Account setCreditcardsTotalBalance(BigDecimal creditcardsTotalBalance) {
        this.creditcardsTotalBalance = creditcardsTotalBalance;
        return this;
    }

    public String getCreditcardsTotalCurrency() {
        return creditcardsTotalCurrency;
    }

    public Account setCreditcardsTotalCurrency(String creditcardsTotalCurrency) {
        this.creditcardsTotalCurrency = creditcardsTotalCurrency;
        return this;
    }

    public String getIban() {
        return iban;
    }

    public Account setIban(String iban) {
        this.iban = iban;
        return this;
    }

    public String getCreditcardsTotalDate() {
        return creditcardsTotalDate;
    }

    public Account setCreditcardsTotalDate(String creditcardsTotalDate) {
        this.creditcardsTotalDate = creditcardsTotalDate;
        return this;
    }

    @Override
    public int compareTo(Account other) {
        return CompteType.fromId(this.getTypeId()).compareTo(CompteType.fromId(other.getTypeId()));
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
        return Objects.equals(accountNumber, account.accountNumber) &&
                Objects.equals(typeId, account.typeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, typeId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("accountNumber", accountNumber)
                .append("currency", currency)
                .append("type", type)
                .append("subtype", subtype)
                .toString();
    }

}
