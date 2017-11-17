package com.github.rjansem.microservices.training.transaction.mapper;

import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.Beneficiaire;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.CompteOrdre;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.CompteSubType;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.OrdreDetail;
import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.Transaction;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author mbouhamyd
 */
public class OrdreDetailToTransactionMapperTest {
    @Test
    public void map_CRDT() throws Exception {

        OrdreDetail ordre = new OrdreDetail();
        ordre.setId("WBSCT3_O_58501245c5438a1c_0");
        CompteOrdre compteOrdre = new CompteOrdre();
        compteOrdre.setNumero("88888888888");
        compteOrdre.setType("COMPTE A VUE");
        compteOrdre.setIntitule("Compte A");
        compteOrdre.setIntitulePersonnalise("Mon libelle perso");
        compteOrdre.setSolde("");
        compteOrdre.setDevise(10);
        compteOrdre.setProfil("E");
        compteOrdre.setIban("FR7630788001008888888888830");
        compteOrdre.setNature("DAV");
        ordre.setCompteEmetteur(compteOrdre);
        ordre.setDateExecutionSouhaitee("01-05-2017");
        ordre.setCodeApplication("WBSCT3");
        ordre.setStatut("4096");
        ordre.setMontant("100.000000");
        ordre.setCodeDevise("EUR");
        ordre.setDateCreation("13-12-2016");
        ordre.setMedia("U");
        ordre.setCodeOperation("INT");
        Beneficiaire beneficiaire = new Beneficiaire();
        beneficiaire.setLibelle("Benef");
        beneficiaire.setMotif("Loyer");
        beneficiaire.setIban("FR7630003001001111111111132");
        beneficiaire.setReference("NOTPROVIDED");
        ordre.getBeneficiaires().add(beneficiaire);

        OrdreDetailToTransactionMapper ordreToTransaction = new OrdreDetailToTransactionMapper();
        Transaction transaction = ordreToTransaction.map(ordre, "SINGLE", null);

        Transaction transactionExcepted = new Transaction();
        transactionExcepted.setId("WBSCT3_O_58501245c5438a1c_0");
        transactionExcepted.setStatus("4096");
        transactionExcepted.setAccountId("FR7630788001008888888888830");
        transactionExcepted.setAccountNumber("88888888888");
        transactionExcepted.setType(CompteSubType.findLibelleById("COMPTE A VUE").getType());
        transactionExcepted.setTypeId(CompteSubType.findLibelleById("COMPTE A VUE").getTypeId());
        transactionExcepted.setSubtype("COMPTE A VUE");
        transactionExcepted.setSubtypeId(CompteSubType.findLibelleById("COMPTE A VUE").getId());
        transactionExcepted.setInitiationDate("2016-12-13T00:00:00Z");
        transactionExcepted.setBookingDateTime("2017-05-01T00:00:00Z");
        //transaction.setCounterpartyAccountNumber("?");
        transactionExcepted.setCounterpartyIban("FR7630003001001111111111132");
        transactionExcepted.setCounterpartyId("FR7630003001001111111111132");
        transactionExcepted.setCounterpartyLastName("Benef");
        BigDecimal amount = new BigDecimal("-100.000000");
        transactionExcepted.setInstructedAmount(amount);
        transactionExcepted.setInstructedCurrency("EUR");
        transactionExcepted.setCreditDebitIndicator("CRDT");
        transactionExcepted.setPaymentMode("SINGLE");

        assertThat(transaction).isEqualTo(transactionExcepted);
    }

    @Test
    public void map_DBT() throws Exception {

        OrdreDetail ordre = new OrdreDetail();
        ordre.setId("WBSCT3_O_58501245c5438a1c_0");
        CompteOrdre compteOrdre = new CompteOrdre();
        compteOrdre.setNumero("88888888888");
        compteOrdre.setType("COMPTE A VUE");
        compteOrdre.setIntitule("Compte A");
        compteOrdre.setIntitulePersonnalise("Mon libelle perso");
        compteOrdre.setSolde("");
        compteOrdre.setDevise(10);
        compteOrdre.setProfil("E");
        compteOrdre.setIban("FR7630788001008888888888830");
        compteOrdre.setNature("DAV");
        ordre.setCompteEmetteur(compteOrdre);
        ordre.setDateExecutionSouhaitee("01-05-2017");
        ordre.setCodeApplication("WBSCT3");
        ordre.setStatut("4096");
        ordre.setMontant("-100.000000");
        ordre.setCodeDevise("EUR");
        ordre.setDateCreation("13-12-2016");
        ordre.setMedia("U");
        ordre.setCodeOperation("PERI");
        Beneficiaire beneficiaire = new Beneficiaire();
        beneficiaire.setLibelle("Benef");
        beneficiaire.setMotif("Loyer");
        beneficiaire.setIban("FR7630003001001111111111132");
        beneficiaire.setReference("NOTPROVIDED");
        ordre.getBeneficiaires().add(beneficiaire);

        OrdreDetailToTransactionMapper ordreToTransaction = new OrdreDetailToTransactionMapper();
        Transaction transaction = ordreToTransaction.map(ordre, "SINGLE", null);

        Transaction transactionExcepted = new Transaction();
        transactionExcepted.setId("WBSCT3_O_58501245c5438a1c_0");
        transactionExcepted.setStatus("4096");
        transactionExcepted.setAccountId("FR7630788001008888888888830");
        transactionExcepted.setAccountNumber("88888888888");
        transactionExcepted.setType(CompteSubType.findLibelleById("COMPTE A VUE").getType());
        transactionExcepted.setTypeId(CompteSubType.findLibelleById("COMPTE A VUE").getTypeId());
        transactionExcepted.setSubtype("COMPTE A VUE");
        transactionExcepted.setSubtypeId(CompteSubType.findLibelleById("COMPTE A VUE").getId());
        transactionExcepted.setInitiationDate("2016-12-13T00:00:00Z");
        transactionExcepted.setBookingDateTime("2017-05-01T00:00:00Z");
        //transaction.setCounterpartyAccountNumber("?");
        transactionExcepted.setCounterpartyIban("FR7630003001001111111111132");
        transactionExcepted.setCounterpartyId("FR7630003001001111111111132");
        transactionExcepted.setCounterpartyLastName("Benef");
        BigDecimal amount = new BigDecimal("100.000000");
        transactionExcepted.setInstructedAmount(amount);
        transactionExcepted.setInstructedCurrency("EUR");
        transactionExcepted.setCreditDebitIndicator("CRDT");
        transactionExcepted.setPaymentMode("SINGLE");

        assertThat(transaction).isEqualTo(transactionExcepted);
    }

}