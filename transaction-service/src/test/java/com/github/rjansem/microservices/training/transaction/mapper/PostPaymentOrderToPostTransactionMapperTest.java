package com.github.rjansem.microservices.training.transaction.mapper;

import com.github.rjansem.microservices.training.transaction.domain.efs.post.PostTransaction;
import com.github.rjansem.microservices.training.transaction.domain.pbi.PaymentOrder;
import com.github.rjansem.microservices.training.transaction.domain.pbi.TransactionInfo;
import com.github.rjansem.microservices.training.transaction.domain.pbi.account.TransactionAccount;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by aazzerrifi on 05/01/2017.
 */
public class PostPaymentOrderToPostTransactionMapperTest {

    PostPaymentOrderToPostTransactionMapper postPaymentOrderToPostTransactionMapper = new PostPaymentOrderToPostTransactionMapper ( );

    @Test
    public void map() throws Exception {
        PostTransaction postTransaction = new PostTransaction (
                "login", "2",
                "codeApplication",
                "setId",
                "setIban",
                "setId",
                "4",
                "01012001",
                "setMemo",
                "setPaymentMode",
                "setInstructedCurrency");

        PaymentOrder input = new PaymentOrder ( );
        TransactionAccount account = new TransactionAccount ( );
        account.setId ("setId");
        account.setIban ("setIban");
        input.setDebitAccount (account);
        input.setCreditAccount (account);
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setInstructedAmount (new BigDecimal ("4"));
        transactionInfo.setExecutionDateTime("2001-01-01T10:21:15.223Z");
        transactionInfo.setMemo ("setMemo");
        transactionInfo.setPaymentMode ("setPaymentMode");
        transactionInfo.setInstructedCurrency ("setInstructedCurrency");
        input.setTransactionInfo (transactionInfo);
        PostTransaction expected = postPaymentOrderToPostTransactionMapper.map(input, "login");
        assertThat(expected.getMotif()).isEqualTo(postTransaction.getMotif());
        assertThat(expected.getNbDecimales()).isEqualTo(postTransaction.getNbDecimales());
        assertThat(expected.getMontant()).isEqualTo(postTransaction.getMontant());
        assertThat(expected.getIbanCompteBeneficiaire()).isEqualTo(postTransaction.getIbanCompteBeneficiaire());
        assertThat(expected.getIbanCompteEmetteur()).isEqualTo(postTransaction.getIbanCompteEmetteur());
    }

    @Test
    public void map_Intern() throws Exception {
        PostTransaction postTransaction = new PostTransaction(
                "login", "2",
                "codeApplication",
                "setIban",
                "setIban",
                "",
                "4",
                "01012001",
                "setMemo",
                "setPaymentMode",
                "setInstructedCurrency");

        PaymentOrder input = new PaymentOrder();
        TransactionAccount account = new TransactionAccount();
        account.setId("setIban");
        account.setIban("setIban");
        input.setDebitAccount(account);
        input.setCreditAccount(account);
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setInstructedAmount(new BigDecimal("4"));
        transactionInfo.setExecutionDateTime("2001-01-01T10:21:15.223Z");
        transactionInfo.setMemo("setMemo");
        transactionInfo.setPaymentMode("setPaymentMode");
        transactionInfo.setInstructedCurrency("setInstructedCurrency");
        input.setTransactionInfo(transactionInfo);
        PostTransaction expected = postPaymentOrderToPostTransactionMapper.map(input, "login");
        assertThat(expected.getMotif()).isEqualTo(postTransaction.getMotif());
        assertThat(expected.getNbDecimales()).isEqualTo(postTransaction.getNbDecimales());
        assertThat(expected.getMontant()).isEqualTo(postTransaction.getMontant());
        assertThat(expected.getIbanCompteBeneficiaire()).isEqualTo(postTransaction.getIbanCompteBeneficiaire());
        assertThat(expected.getIbanCompteEmetteur()).isEqualTo(postTransaction.getIbanCompteEmetteur());
    }

}