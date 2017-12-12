package com.github.rjansem.microservices.training.account.mapper.account;

import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteCourant;
import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteEpargne;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.domain.pbi.account.AccountGroup;
import com.github.rjansem.microservices.training.account.domain.pbi.account.AccountGroupType;
import com.github.rjansem.microservices.training.account.domain.pbi.account.AccountOverview;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

import static com.github.rjansem.microservices.training.account.mapper.account.MapperConstants.DEVISE_EUR;


/**
 * Mapper transformant des {@link CompteCourant} et {@link CompteEpargne} en {@link AccountOverview}
 *
 * @author rjansem
 * @author rjansem
 */
public class AccountsToAccountOverviewMapper {

    public AccountsToAccountOverviewMapper() {
    }

    public AccountOverview map(List<AccountGroup> groups) {
        AccountOverview accountOverview = new AccountOverview();
        if (groups.isEmpty()) {
            return accountOverview;
        }
        accountOverview.setOverallWealth(calculGroupOverallWealth(groups));
        accountOverview.setOverallWealthCurrency(MapperConstants.DEVISE_EUR);
        accountOverview.setAccountGroups(groups);
        return accountOverview;
    }

    public AccountGroup mapInvestments(List<Account> investmentsAccounts) {
        AccountGroup investmentsGroup = new AccountGroup();
        if (investmentsAccounts.isEmpty()) {
            return investmentsGroup;
        }
        investmentsGroup.setGroupId(AccountGroupType.INVESTMENTS.getId());
        investmentsGroup.setGroupName(AccountGroupType.INVESTMENTS.getLibelle());
        investmentsGroup.setTotalBalance(calculGroupTotalBalance(investmentsAccounts));
        investmentsGroup.setTotalBalanceDate(Instant.now());
        investmentsGroup.setCurrency(MapperConstants.DEVISE_EUR);
        investmentsGroup.setAccounts(investmentsAccounts);
        return investmentsGroup;
    }

    public AccountGroup mapCheckingsAndSavings(List<Account> checkingsAndSavingsAccounts) {
        AccountGroup checkingsAndSavingsGroup = new AccountGroup();
        if (checkingsAndSavingsAccounts.isEmpty()) {
            return checkingsAndSavingsGroup;
        }
        checkingsAndSavingsGroup.setGroupId(AccountGroupType.CURRENT_SAVING.getId());
        checkingsAndSavingsGroup.setGroupName(AccountGroupType.CURRENT_SAVING.getLibelle());
        checkingsAndSavingsGroup.setTotalBalance(calculGroupTotalBalance(checkingsAndSavingsAccounts));
        checkingsAndSavingsGroup.setTotalBalanceDate(Instant.now());
        checkingsAndSavingsGroup.setCurrency(MapperConstants.DEVISE_EUR);
        checkingsAndSavingsGroup.setAccounts(checkingsAndSavingsAccounts);
        return checkingsAndSavingsGroup;
    }

    public AccountGroup mapLoans(List<Account> loansAccounts) {
        AccountGroup loansGroup = new AccountGroup();
        if (loansAccounts.isEmpty()) {
            return loansGroup;
        }
        loansGroup.setGroupId(AccountGroupType.LOAN.getId());
        loansGroup.setGroupName(AccountGroupType.LOAN.getLibelle());
        loansGroup.setTotalBalance(calculGroupTotalBalance(loansAccounts));
        loansGroup.setTotalBalanceDate(Instant.now());
        loansGroup.setCurrency(MapperConstants.DEVISE_EUR);
        loansGroup.setAccounts(loansAccounts);
        return loansGroup;
    }

    public AccountGroup mapBulocs(List<Account> bulocAccounts) {
        AccountGroup bulocsGroup = new AccountGroup();
        if (bulocAccounts.isEmpty()) {
            return bulocsGroup;
        }
        bulocsGroup.setGroupId(AccountGroupType.BULOC.getId());
        bulocsGroup.setGroupName(AccountGroupType.BULOC.getLibelle());
        bulocsGroup.setTotalBalance(calculGroupTotalBalance(bulocAccounts));
        bulocsGroup.setTotalBalanceDate(Instant.now());
        bulocsGroup.setCurrency(MapperConstants.DEVISE_EUR);
        bulocsGroup.setAccounts(bulocAccounts);
        return bulocsGroup;
    }

    private BigDecimal calculGroupTotalBalance(List<Account> accounts) {
        return accounts.stream()
                .map(Account::getBookedBalance)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculGroupOverallWealth(List<AccountGroup> accountGroups) {
        return accountGroups.stream()
                .map(AccountGroup::getTotalBalance)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
