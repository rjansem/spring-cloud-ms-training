package com.github.rjansem.microservices.training.account.domain.pbi.account;

import com.github.rjansem.microservices.training.commons.domain.IdentifiableDomain;
import com.github.rjansem.microservices.training.commons.domain.PbiBean;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


/**
 * Bean représentant le compte credit d'un client
 *
 * @author rjansem
 */
public class LoanAccount implements IdentifiableDomain, PbiBean {

    @NotEmpty
    private String id;

    @NotEmpty
    private String accountNumber;

    private String alias;

    @NotEmpty
    private String type;

    @NotEmpty
    private String typeId;

    @NotEmpty
    private String subtype;

    @NotEmpty
    private String subtypeId;

    private String clientFirstName;

    private String clientMiddleName;

    private String clientLastName;

    @NotEmpty
    private BigDecimal bookedBalance;

    @NotEmpty
    private String bookedBalanceCurrency;

    private BigDecimal initialBalance;

    private String initialBalanceCurrency;

    @NotEmpty
    private LocalDate openingDate;

    @NotEmpty
    private LocalDate expirationDate;

    private String interest;

    private String mode;

    private String frequency;

    private LocalDate nextDate;

    @NotEmpty
    private BigDecimal nextAmount;

    @NotEmpty
    private String currency;

    private String interestType;

    private String beneficiary;

    private String targetNumber;

    private String targetIBAN;

    public LoanAccount(String id, String accountNumber, String type, String typeId, String subtype, String subtypeId,
                       BigDecimal bookedBalance, String bookedBalanceCurrency, LocalDate openingDate, LocalDate expirationDate,
                       BigDecimal nextAmount, String currency) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.type = type;
        this.typeId = typeId;
        this.subtype = subtype;
        this.subtypeId = subtypeId;
        this.bookedBalance = bookedBalance;
        this.bookedBalanceCurrency = bookedBalanceCurrency;
        this.openingDate = openingDate;
        this.expirationDate = expirationDate;
        this.nextAmount = nextAmount;
        this.currency = currency;
    }

    public LoanAccount() {
        // for jakson
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public LoanAccount setId(String setId) {
        this.id = setId;
        return this;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public LoanAccount setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public String getAlias() {
        return alias;
    }

    public LoanAccount setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public String getType() {
        return type;
    }

    public LoanAccount setType(String type) {
        this.type = type;
        return this;
    }

    public String getTypeId() {
        return typeId;
    }

    public LoanAccount setTypeId(String typeId) {
        this.typeId = typeId;
        return this;
    }

    public String getSubtype() {
        return subtype;
    }

    public LoanAccount setSubtype(String subtype) {
        this.subtype = subtype;
        return this;
    }

    public String getSubtypeId() {
        return subtypeId;
    }

    public LoanAccount setSubtypeId(String subtypeId) {
        this.subtypeId = subtypeId;
        return this;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public LoanAccount setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
        return this;
    }

    public String getClientMiddleName() {
        return clientMiddleName;
    }

    public LoanAccount setClientMiddleName(String clientMiddleName) {
        this.clientMiddleName = clientMiddleName;
        return this;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public LoanAccount setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
        return this;
    }

    public BigDecimal getBookedBalance() {
        return bookedBalance;
    }

    public LoanAccount setBookedBalance(BigDecimal bookedBalance) {
        this.bookedBalance = bookedBalance;
        return this;
    }

    public String getBookedBalanceCurrency() {
        return bookedBalanceCurrency;
    }

    public LoanAccount setBookedBalanceCurrency(String bookedBalanceCurrency) {
        this.bookedBalanceCurrency = bookedBalanceCurrency;
        return this;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public LoanAccount setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
        return this;
    }

    public String getInitialBalanceCurrency() {
        return initialBalanceCurrency;
    }

    public LoanAccount setInitialBalanceCurrency(String initialBalanceCurrency) {
        this.initialBalanceCurrency = initialBalanceCurrency;
        return this;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public LoanAccount setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
        return this;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public LoanAccount setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public String getInterest() {
        return interest;
    }

    public LoanAccount setInterest(String interest) {
        this.interest = interest;
        return this;
    }

    public String getMode() {
        return mode;
    }

    public LoanAccount setMode(String mode) {
        this.mode = mode;
        return this;
    }

    public String getFrequency() {
        return frequency;
    }

    public LoanAccount setFrequency(String frequency) {
        this.frequency = frequency;
        return this;
    }

    public LocalDate getNextDate() {
        return nextDate;
    }

    public LoanAccount setNextDate(LocalDate nextDate) {
        this.nextDate = nextDate;
        return this;
    }

    public BigDecimal getNextAmount() {
        return nextAmount;
    }

    public LoanAccount setNextAmount(BigDecimal nextAmount) {
        this.nextAmount = nextAmount;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public LoanAccount setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getInterestType() {
        return interestType;
    }

    public LoanAccount setInterestType(String interestType) {
        this.interestType = interestType;
        return this;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public LoanAccount setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
        return this;
    }

    public String getTargetNumber() {
        return targetNumber;
    }

    public LoanAccount setTargetNumber(String targetNumber) {
        this.targetNumber = targetNumber;
        return this;
    }

    public String getTargetIBAN() {
        return targetIBAN;
    }

    public LoanAccount setTargetIBAN(String targetIBAN) {
        this.targetIBAN = targetIBAN;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoanAccount)) {
            return false;
        }
        LoanAccount that = (LoanAccount) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("accountNumber", accountNumber)
                .append("type", type)
                .append("bookedBalance", bookedBalance)
                .append("bookedBalanceCurrency", bookedBalanceCurrency)
                .append("openingDate", openingDate)
                .append("nextAmount", nextAmount)
                .toString();
    }
}
