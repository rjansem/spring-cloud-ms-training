package com.github.rjansem.microservices.training.account;

import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.domain.pbi.account.AccountGroup;
import com.github.rjansem.microservices.training.account.domain.pbi.account.AccountGroupType;
import com.github.rjansem.microservices.training.account.domain.pbi.account.LoanAccount;
import com.github.rjansem.microservices.training.commons.domain.utils.DateUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType.*;

/**
 * Created by aazzerrifi on 13/02/2017.
 */
public class CasDeTestsUtils {

    /*
    Account Group
     */
    public static AccountGroup getInvestmentsGroup() {
        AccountGroup expected = new AccountGroup();
        expected.setGroupId(AccountGroupType.INVESTMENTS.getId());
        expected.setGroupName(AccountGroupType.INVESTMENTS.getLibelle());
        expected.setTotalBalance(new BigDecimal("1000"));
        expected.setTotalBalanceDate(Instant.now());
        expected.setCurrency("EUR");
        List<Account> investmentsAccounts = new ArrayList<>();
        Account accountPF = getAccountPF_80000351();
        accountPF.setBookedBalance(new BigDecimal("200"));
        Account accountLI = getAccountLI_80000001();
        accountLI.setBookedBalance(new BigDecimal("800"));
        investmentsAccounts.add(accountPF);
        investmentsAccounts.add(accountLI);
        expected.setAccounts(investmentsAccounts);
        return expected;
    }

    public static AccountGroup getCheckingsAndSavingsGroup() {
        AccountGroup checkingsAndSavingsGroup = new AccountGroup();
        checkingsAndSavingsGroup.setGroupId(AccountGroupType.CURRENT_SAVING.getId());
        checkingsAndSavingsGroup.setGroupName(AccountGroupType.CURRENT_SAVING.getLibelle());
        checkingsAndSavingsGroup.setTotalBalance(new BigDecimal("2000"));
        checkingsAndSavingsGroup.setTotalBalanceDate(Instant.now());
        checkingsAndSavingsGroup.setCurrency("EUR");
        List<Account> checkingsAndSavingsAccounts = new ArrayList<>();
        checkingsAndSavingsAccounts.add(getAccountCC().setBookedBalance(new BigDecimal("1000")));
        checkingsAndSavingsAccounts.add(getAccountEp().setBookedBalance(new BigDecimal("1000")));
        checkingsAndSavingsGroup.setAccounts(checkingsAndSavingsAccounts);
        return checkingsAndSavingsGroup;
    }

    public static AccountGroup getLoansGroup() {
        AccountGroup loansGroup = new AccountGroup();
        loansGroup.setGroupId(AccountGroupType.LOAN.getId());
        loansGroup.setGroupName(AccountGroupType.LOAN.getLibelle());
        loansGroup.setTotalBalance(new BigDecimal("1000"));
        loansGroup.setTotalBalanceDate(Instant.now());
        loansGroup.setCurrency("EUR");
        List<Account> loansAccounts = new ArrayList<>();
        loansAccounts.add(CasDeTestsUtils.getAccountCredit_A5_0023514().setBookedBalance(new BigDecimal("1000")));
        loansGroup.setAccounts(loansAccounts);
        return loansGroup;
    }

    public static AccountGroup getBulocGroup() {
        AccountGroup bulocsGroup = new AccountGroup();
        bulocsGroup.setGroupId(AccountGroupType.BULOC.getId());
        bulocsGroup.setGroupName(AccountGroupType.BULOC.getLibelle());
        bulocsGroup.setTotalBalance(new BigDecimal("1000"));
        bulocsGroup.setTotalBalanceDate(Instant.now());
        bulocsGroup.setCurrency("EUR");
        List<Account> accounts = new ArrayList<>();
        accounts.add(CasDeTestsUtils.getAccountBulocDetails().setBookedBalance(new BigDecimal("1000")));
        bulocsGroup.setAccounts(accounts);
        return bulocsGroup;
    }

    /*

 */
    public static Account getAccountCC() {
        Account account = new Account();
        account.setAccountNumber("00000000002");
        account.setId("FR7630788001002504780000342");
        account.setSubtype("COMPTE A VUE");
        account.setSubtypeId("CAV");
        account.setCreditcardsTotalBalance(new BigDecimal("1234"));
        account.setTypeId(COURANT.getId());
        account.setType(COURANT.getLibelle());
        account.setCurrency("EUR");
        account.setBookedBalanceCurrency(account.getCurrency());
        account.setCreditcardsTotalCurrency("EUR");
        account.setBookedBalance(new BigDecimal("52312.75"));
        account.setBookedBalanceDate(DateUtils.convertEfsDateToPibDate("11-11-2017"));
        account.setIban("FR7630788001002504780000342");
        account.setCreditcardCount(0);
        account.setCreditcardsTotalDate(DateUtils.convertEfsDateToPibDate("11-11-2017"));
        return account;
    }

    public static Account getAccountEp() {
        Account accountTest = new Account();
        accountTest.setAccountNumber("00000000003");
        accountTest.setId("FR7630788001002504780000343");
        accountTest.setCurrency("EUR");
        accountTest.setBookedBalanceCurrency(accountTest.getCurrency());
        accountTest.setSubtype("PEA");
        accountTest.setSubtypeId("PEA");
        accountTest.setTypeId(EPARGNE.getId());
        accountTest.setType(EPARGNE.getLibelle());
        accountTest.setBookedBalance(new BigDecimal("900.77"));
        accountTest.setBookedBalanceDate(DateUtils.convertEfsDateToPibDate("11-11-2017"));
        accountTest.setIban("FR7630788001002504780000343");
        accountTest.setCreditcardCount(0);
        return accountTest;
    }

    public static Account getAccountDat() {
        Account accountTest = new Account();
        accountTest.setAccountNumber("25138710002");
        accountTest.setId("35964");
        accountTest.setDate(DateUtils.convertEfsDateToLocalDate("20-11-2015"));
        accountTest.setCurrency("EUR");
        accountTest.setTypeId(DEPOT_A_TERME.getId());
        accountTest.setType(DEPOT_A_TERME.getLibelle());
        accountTest.setBookedBalance(new BigDecimal("1800000.000000"));
        accountTest.setBookedBalanceCurrency(accountTest.getCurrency());
        accountTest.setBookedBalanceDate(DateUtils.convertEfsDateToPibDate("10-11-2015"));
        return accountTest;
    }

    public static Account getAccountCC_A4() {
        Account accountTest = new Account();
        accountTest.setAccountNumber("00000000001");
        accountTest.setId("FR7630788001002504780000341");
        accountTest.setSubtype("COMPTE A VUE");
        accountTest.setCurrency("EUR");
        accountTest.setCreditcardsTotalCurrency("EUR");
        accountTest.setBookedBalanceCurrency(accountTest.getCurrency());
        accountTest.setTypeId(COURANT.getId());
        accountTest.setType(COURANT.getLibelle());
        accountTest.setSubtypeId("CAV");
        accountTest.setBookedBalance(new BigDecimal("1512.75"));
        accountTest.setCreditcardsTotalBalance(new BigDecimal("-393.920000"));
        accountTest.setBookedBalanceDate(DateUtils.convertEfsDateToPibDate("11-11-2017"));
        accountTest.setIban("FR7630788001002504780000341");
        accountTest.setCreditcardsTotalDate(DateUtils.convertEfsDateToPibDate("11-11-2017"));
        accountTest.setCreditcardCount(2);
        return accountTest;
    }

    public static Account getAccountCC_A5_USER() {
        Account account = new Account();
        account.setAccountNumber("00000000001");
        account.setId("FR7630788001002504780000351");
        account.setBookedBalance(new BigDecimal("2312.75"));
        account.setBookedBalanceDate(DateUtils.convertEfsDateToPibDate("11-11-2016"));
        account.setCreditcardsTotalBalance(new BigDecimal("-1000.000000"));
        account.setSubtype("COMPTE A VUE");
        account.setSubtypeId("CAV");
        account.setTypeId(COURANT.getId());
        account.setType(COURANT.getLibelle());
        account.setCurrency("EUR");
        account.setCreditcardsTotalCurrency("EUR");
        account.setBookedBalanceCurrency(account.getCurrency());
        account.setIban("FR7630788001002504780000351");
        account.setCreditcardsTotalDate(DateUtils.convertEfsDateToPibDate("09-11-2016"));
        account.setCreditcardCount(2);
        return account;
    }

    public static Account getAccountEp_A5_USER() {
        Account accountTest = new Account();
        accountTest.setAccountNumber("00000000003");
        accountTest.setId("FR7630788001002504780000353");
        accountTest.setBookedBalance(new BigDecimal("29000.000000"));
        accountTest.setBookedBalanceDate(DateUtils.convertEfsDateToPibDate("11-11-2016"));
        accountTest.setCurrency("EUR");
        accountTest.setBookedBalanceCurrency(accountTest.getCurrency());
        accountTest.setSubtype("PEA");
        accountTest.setSubtypeId("PEA");
        accountTest.setTypeId(EPARGNE.getId());
        accountTest.setType(EPARGNE.getLibelle());
        accountTest.setIban("FR7630788001002504780000353");
        accountTest.setCreditcardCount(0);
        return accountTest;
    }

/*

 */

    public static Account getAccountBulocDetails() {
        Account account = new Account();
        account.setId("24531");
        account.setAccountNumber("24531");
        account.setCurrency("EUR");
        account.setNextWriteOffCurrency("EUR");
        account.setBookedBalance(new BigDecimal("121747.000000"));
        account.setBookedBalanceCurrency("EUR");
        account.setTypeId(ENGAGEMENT_PAR_SIGNATURE.getId());
        account.setType(ENGAGEMENT_PAR_SIGNATURE.getLibelle());
        account.setSubtypeId("AUTRES GARANTIES FINANCIERES  ");
        account.setSubtype("AUTRES GARANTIES FINANCIERES  ");
        account.setDate(DateUtils.convertEfsDateToLocalDate("15-06-2016"));
        account.setNextWriteOffAmount(new BigDecimal("517.180000"));
        return account;
    }

    public static Account getAccountBuloc() {
        Account account = new Account();
        account.setId("24531");
        account.setAccountNumber("24531");
        account.setCurrency("EUR");
        account.setNextWriteOffCurrency("EUR");
        account.setSubtypeId("AUTRES GARANTIES FINANCIERES  ");
        account.setSubtype("AUTRES GARANTIES FINANCIERES  ");
        account.setBookedBalance(new BigDecimal("121747.000000"));
        account.setBookedBalanceCurrency("EUR");
        account.setDate(DateUtils.convertEfsDateToLocalDate("15-06-2016"));
        account.setTypeId(ENGAGEMENT_PAR_SIGNATURE.getId());
        account.setType(ENGAGEMENT_PAR_SIGNATURE.getLibelle());
        return account;
    }

    /*

     */
    public static Account getAccountLI_80000001() {
        Account account = new Account();
        account.setId("80000001");
        account.setAccountNumber("80000001");
        account.setCurrency("EUR");
        account.setTypeId(CompteType.ASSURANCE_VIE.getId());
        account.setCompany("LA MONDIALE EUROPARTNER");
        account.setBookedBalance(new BigDecimal("7257136.56"));
        account.setSubtypeId("Externe");
        account.setSubtype("LIFE MOBILITY EVOLUTION VIE");
        account.setType(CompteType.ASSURANCE_VIE.getLibelle());
        account.setBookedBalanceCurrency("EUR");
        account.setBookedBalanceDate(DateUtils.convertEfsDateToPibDate("19-01-2015"));
        return account;
    }

    public static Account getAccountPF_80000351() {
        Account account = new Account();
        account.setId("80000351");
        account.setAccountNumber("80000351");
        account.setCurrency("EUR");
        account.setTypeId("PortefeuilleTitres");
        account.setSubtype("COMPTE TITRE PEA GERE");
        account.setType(CompteType.PORTEFEUILLE_TITRES.getLibelle());
        account.setSubtypeId("PEA_GERE");
        account.setBookedBalance(new BigDecimal("77468.17"));
        account.setBookedBalanceCurrency("EUR");
        account.setBookedBalanceDate(DateUtils.convertEfsDateToPibDate("19-07-2015"));
        account.setIban("");
        return account;
    }

    public static Account getAccountLI_800003410() {
        Account account = new Account();
        account.setId("800003410");
        account.setAccountNumber("800003410");
        account.setCurrency("EUR");
        account.setTypeId(CompteType.ASSURANCE_VIE.getId());
        account.setCompany("GENERALI FRANCE VIE");
        account.setBookedBalance(new BigDecimal("2327136.56"));
        account.setBookedBalanceCurrency("EUR");
        account.setSubtypeId("Externe");
        account.setSubtype("PREFERENCE CAPITALISATION PARTIC");
        account.setType(CompteType.ASSURANCE_VIE.getLibelle());
        return account;
    }

    public static Account getAccountPF_80000341() {
        Account account = new Account();
        account.setId("80000341");
        account.setAccountNumber("80000341");
        account.setCurrency("EUR");
        account.setTypeId("PortefeuilleTitres");
        account.setSubtype("COMPTE TITRE PEA GERE");
        account.setType(CompteType.PORTEFEUILLE_TITRES.getLibelle());
        account.setSubtypeId("PEA_GERE");
        account.setBookedBalance(new BigDecimal("77468.17"));
        account.setBookedBalanceCurrency("EUR");
        return account;
    }

    /*

     */
    public static Account getAccountCredit_A5_0023514() {
        Account account = new Account();
        account.setId("0023514");
        account.setAccountNumber("0023514");
        account.setCurrency("EUR");
        account.setNextWriteOffCurrency("EUR");
        account.setBookedBalance(new BigDecimal("1800000.000000"));
        account.setBookedBalanceCurrency("EUR");
        account.setTypeId(CREDIT.getId());
        account.setType(CREDIT.getLibelle());
        account.setSubtype("CREDIT DE TRESORERIE");
        account.setSubtypeId("CREDIT DE TRESORERIE");
        account.setDate(DateUtils.convertEfsDateToLocalDate("31-03-2016"));
        account.setNextWriteOffAmount(new BigDecimal("5915.000000"));
        return account;
    }

    /*

     */
    public static LoanAccount getLoanAccount_A5R1_0023514() {
        LoanAccount loanAccount = new LoanAccount(
                "0023514",
                "0023514",
                CREDIT.getLibelle(),
                CREDIT.getId(),
                "CREDIT DE TRESORERIE",
                "CREDIT DE TRESORERIE",
                new BigDecimal("1800000.000000"),
                "EUR",
                DateUtils.convertEfsDateToLocalDate("30-12-2014"),
                DateUtils.convertEfsDateToLocalDate("31-12-2021"),
                new BigDecimal("5915.000000"),
                "EUR");
        loanAccount.setInitialBalance(new BigDecimal("2000000.000000"));
        loanAccount.setInitialBalanceCurrency("EUR");
        loanAccount.setInterest("EURI3M+1,30%");
        loanAccount.setClientLastName("b mandat pujocert -0000006-");
        loanAccount.setMode("MODE D'AMORTISSEMENT SPECIALE");
        loanAccount.setFrequency("ANNUELLE");
        loanAccount.setNextDate(DateUtils.convertEfsDateToLocalDate("31-03-2016"));
        loanAccount.setInterestType("ECHEANCE INTERETS");
        return loanAccount;
    }

    public static LoanAccount getBulocAccount_A5R1_24531() {
        LoanAccount loanAccount = new LoanAccount(
                "24531",
                "24531",
                ENGAGEMENT_PAR_SIGNATURE.getLibelle(),
                ENGAGEMENT_PAR_SIGNATURE.getId(),
                "AUTRES GARANTIES FINANCIERES  ",
                "AUTRES GARANTIES FINANCIERES  ",
                new BigDecimal("121747.000000"),
                "EUR",
                DateUtils.convertEfsDateToLocalDate("15-06-2010"),
                DateUtils.convertEfsDateToLocalDate("15-06-2016"),
                new BigDecimal("517.180000"),
                "EUR");
        loanAccount.setInitialBalance(new BigDecimal("121747.000000"));
        loanAccount.setInitialBalanceCurrency("EUR");
        loanAccount.setBeneficiary("");
        return loanAccount;
    }

    public static LoanAccount getDatAccount_A5R1_35964() {
        LoanAccount account = new LoanAccount(
                "35964",
                "35964",
                DEPOT_A_TERME.getLibelle(),
                DEPOT_A_TERME.getId(),
                null,
                null,
                new BigDecimal("1800000.000000"),
                "EUR",
                DateUtils.convertEfsDateToLocalDate("10-11-2015"),
                DateUtils.convertEfsDateToLocalDate("20-11-2015"),
                null,
                "EUR");
        account.setInterest("1.000000");
        account.setClientLastName("CAT");
        account.setTargetNumber("25138710002");
        return account;
    }
}
