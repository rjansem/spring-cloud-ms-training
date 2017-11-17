package com.github.rjansem.microservices.training.account.mapper.account;

import com.github.rjansem.microservices.training.account.CasDeTestsUtils;
import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteCourant;
import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteEpargne;
import com.github.rjansem.microservices.training.account.domain.pbi.account.AccountGroup;
import com.github.rjansem.microservices.training.account.domain.pbi.account.AccountOverview;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.github.rjansem.microservices.training.account.mapper.account.MapperConstants.DEVISE_EUR;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Mapper transformant des {@link CompteCourant} et {@link CompteEpargne} en {@link AccountOverview}
 *
 * @author aazzerrifi
 */
public class AccountsToAccountOverviewMapperTest {
    @Test
    public void map_Empty() throws Exception {
        AccountOverview expected = new AccountOverview();
        List<AccountGroup> groups = new ArrayList<AccountGroup>();
        assertThat(new AccountsToAccountOverviewMapper().map(groups)).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void map() throws Exception {
        AccountOverview expected = new AccountOverview();
        List<AccountGroup> groups = getAccountGroups();
        expected.setOverallWealth(new BigDecimal("5000"));
        expected.setOverallWealthCurrency(DEVISE_EUR);
        expected.setAccountGroups(groups);
        assertThat(new AccountsToAccountOverviewMapper().map(groups)).isEqualToComparingFieldByField(expected);
    }

    private List<AccountGroup> getAccountGroups() {
        List<AccountGroup> groups = new ArrayList<AccountGroup>();
        AccountGroup accountGroup = CasDeTestsUtils.getCheckingsAndSavingsGroup();
        AccountGroup accountGroup_2 = CasDeTestsUtils.getInvestmentsGroup();
        AccountGroup accountGroup_3 = CasDeTestsUtils.getLoansGroup();
        AccountGroup accountGroup_4 = CasDeTestsUtils.getBulocGroup();
        groups.add(accountGroup);
        groups.add(accountGroup_2);
        groups.add(accountGroup_3);
        groups.add(accountGroup_4);
        return groups;
    }

    @Test
    public void mapInvestments_Empty() throws Exception {
        AccountGroup expected = new AccountGroup();
        assertThat(new AccountsToAccountOverviewMapper()
                .mapInvestments(new ArrayList<>()))
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    public void mapInvestments() throws Exception {
        AccountGroup investmentsGroup = CasDeTestsUtils.getInvestmentsGroup();
        assertThat(new AccountsToAccountOverviewMapper()
                .mapInvestments(investmentsGroup.getAccounts()))
                .isEqualToComparingFieldByField(investmentsGroup);
    }

    @Test
    public void mapCheckingsAndSavings_Empty() throws Exception {
        AccountGroup expected = new AccountGroup();
        assertThat(new AccountsToAccountOverviewMapper()
                .mapCheckingsAndSavings(new ArrayList<>()))
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    public void mapCheckingsAndSavings() throws Exception {
        AccountGroup expected = CasDeTestsUtils.getCheckingsAndSavingsGroup();
        assertThat(new AccountsToAccountOverviewMapper()
                .mapCheckingsAndSavings(expected.getAccounts()))
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    public void mapLoans_Empty() throws Exception {
        AccountGroup loansGroupx = new AccountGroup();
        assertThat(new AccountsToAccountOverviewMapper()
                .mapLoans(new ArrayList<>()))
                .isEqualToComparingFieldByField(loansGroupx);
    }

    @Test
    public void mapLoans() throws Exception {
        AccountGroup loansGroup = CasDeTestsUtils.getLoansGroup();
        assertThat(new AccountsToAccountOverviewMapper()
                .mapLoans(loansGroup.getAccounts()))
                .isEqualToComparingFieldByField(loansGroup);
    }

    @Test
    public void mapBulocs_Empty() throws Exception {
        AccountGroup bulocsGroup = new AccountGroup();
        assertThat(new AccountsToAccountOverviewMapper()
                .mapBulocs(new ArrayList<>()))
                .isEqualToComparingFieldByField(bulocsGroup);
    }

    @Test
    public void mapBulocs() throws Exception {
        AccountGroup bulocsGroup = CasDeTestsUtils.getBulocGroup();
        assertThat(new AccountsToAccountOverviewMapper()
                .mapBulocs(bulocsGroup.getAccounts()))
                .isEqualToComparingFieldByField(bulocsGroup);
    }
}