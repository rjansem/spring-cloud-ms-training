package com.github.rjansem.microservices.training.account.domain.pbi.account;

import com.github.rjansem.microservices.training.commons.domain.PbiBean;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Bean représentant un groupe de comptes
 *
 * @author aazzerrifi
 * @author jntakpe
 */
public class AccountGroup implements PbiBean, Comparable<AccountGroup> {

    @NotEmpty
    private String groupId;

    @NotEmpty
    private String groupName;

    @NotNull
    private BigDecimal totalBalance;

    @NotNull
    private Instant totalBalanceDate;

    @NotEmpty
    private String currency;

    private List<Account> accounts = new ArrayList<>();

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Instant getTotalBalanceDate() {
        return totalBalanceDate;
    }

    public void setTotalBalanceDate(Instant totalBalanceDate) {
        this.totalBalanceDate = totalBalanceDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public int compareTo(AccountGroup other) {
        if (this.getGroupId() == null || other.getGroupId() == null) {
            return 0;
        }
        return AccountGroupType.fromId(this.getGroupId()).compareTo(AccountGroupType.fromId(other.getGroupId()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountGroup)) {
            return false;
        }
        AccountGroup that = (AccountGroup) o;
        return Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("groupId", groupId)
                .append("groupName", groupName)
                .append("totalBalance", totalBalance)
                .append("currency", currency)
                .toString();
    }

}
