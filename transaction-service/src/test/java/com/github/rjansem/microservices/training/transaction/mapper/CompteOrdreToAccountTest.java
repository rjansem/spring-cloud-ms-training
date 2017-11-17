package com.github.rjansem.microservices.training.transaction.mapper;

import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.CompteOrdre;
import com.github.rjansem.microservices.training.transaction.domain.pbi.account.Account;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by mbouhamyd on 20/12/2016.
 */
public class CompteOrdreToAccountTest {

    @Test
    public void map() throws Exception {

        CompteOrdre compteOrdre = new CompteOrdre();

        compteOrdre.setNumero("10106896718");
        compteOrdre.setType("COMPTE A VUE");
        compteOrdre.setIntitule("CPTE SPECIAL");
        compteOrdre.setIntitulePersonnalise("test COMPTE A VUE - 10106896718");
        compteOrdre.setSolde("10000.000000");
        compteOrdre.setDevise(10);
        compteOrdre.setProfil("P");
        compteOrdre.setIban("FR7630788001001010689671817");
        compteOrdre.setNature("DAV");
        CompteOrdreToAccountMapper compteOrdreToAccount = new CompteOrdreToAccountMapper();
        Account accountToDebit = compteOrdreToAccount.map(compteOrdre);
        Account accountToDebitExcepted = new Account ();
        accountToDebitExcepted.setType("Compte courant");
        accountToDebitExcepted.setTypeId("DAV");
        accountToDebitExcepted.setSubtype("COMPTE A VUE");
        accountToDebitExcepted.setSubtypeId("CAV");
        accountToDebitExcepted.setId("FR7630788001001010689671817");
        accountToDebitExcepted.setAccountNumber("10106896718");
        accountToDebitExcepted.setAccountLabel("CPTE SPECIAL");
        BigDecimal balance = new BigDecimal("10000.000000");
        accountToDebitExcepted.setBalance(balance);
        accountToDebitExcepted.setCurrency("10");
        Account AccountToDebitExc = new Account ();
        AccountToDebitExc.setAccountNumber("12093909");
        assertThat(accountToDebit).isEqualTo(accountToDebitExcepted);

    }

}