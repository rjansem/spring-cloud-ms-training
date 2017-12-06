package com.github.rjansem.microservices.training.account.domain.pbi.account;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.rjansem.microservices.training.commons.domain.PbiBean;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Bean représentant la synthèse de l'ensemble des comptes
 *
 * @author aazzerrifi
 * @author rjansem
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountOverview implements PbiBean {

    private BigDecimal overallWealth;

    private String overallWealthCurrency;

    private List<AccountGroup> accountGroups = new ArrayList<>();

    public BigDecimal getOverallWealth() {
        return overallWealth;
    }

    public void setOverallWealth(BigDecimal overallWealth) {
        this.overallWealth = overallWealth;
    }

    public String getOverallWealthCurrency() {
        return overallWealthCurrency;
    }

    public void setOverallWealthCurrency(String overallWealthCurrency) {
        this.overallWealthCurrency = overallWealthCurrency;
    }

    public List<AccountGroup> getAccountGroups() {
        return accountGroups;
    }

    public void setAccountGroups(List<AccountGroup> accountGroups) {
        this.accountGroups = accountGroups;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("overallWealth", overallWealth)
                .append("overallWealthCurrency", overallWealthCurrency)
                .toString();
    }
}