package com.github.rjansem.microservices.training.transaction.mapper;

import com.github.rjansem.microservices.training.commons.domain.utils.FindCompteType;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.Beneficiaire;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.OrdreDetail;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.SignatureKBV;
import com.github.rjansem.microservices.training.transaction.domain.pbi.AddressBook;
import com.github.rjansem.microservices.training.transaction.domain.pbi.PaymentOrder;
import com.github.rjansem.microservices.training.transaction.domain.pbi.TransactionInfo;
import com.github.rjansem.microservices.training.transaction.domain.pbi.account.TransactionAccount;
import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.Payment;
import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.SignTransaction;
import com.github.rjansem.microservices.training.transaction.mapper.commun.Utils;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author aazzerrifi
 */
public class OrdreDetailToPaymentOrderMapperTest {

    OrdreDetailToPaymentOrderMapper ordreDetailToPaymentOrderMapper = new OrdreDetailToPaymentOrderMapper ();

    @Test
    public void map() throws Exception {
        PaymentOrder paymentOrder = new PaymentOrder ( );
        paymentOrder.setId ("setId");
        paymentOrder.setStatus ("setStatut");
        paymentOrder.setCreditAccount (new TransactionAccount ( ));
        paymentOrder.setDebitAccount (new TransactionAccount ( ));
        paymentOrder.setTransactionInfo (new TransactionInfo ( ));
        OrdreDetail input = new OrdreDetail ( );
        input.setId ("setId");
        input.setStatut ("setStatut");
        input.setCompteEmetteur (null);
        List<Beneficiaire> beneficiaires = new ArrayList<> ();
        beneficiaires.add(new Beneficiaire());
        input.setBeneficiaires (beneficiaires);
        input.setMontant("10");
        assertThat (ordreDetailToPaymentOrderMapper.map (input))
                .isEqualTo (paymentOrder);
    }

    @Test
    public void map_PaymentOrder_AddressBook() {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setId("setId");
        paymentOrder.setStatus("setStatut");
        paymentOrder.setCreditAccount(new TransactionAccount());
        paymentOrder.setDebitAccount(new TransactionAccount());
        paymentOrder.setTransactionInfo(new TransactionInfo());

        AddressBook book = new AddressBook();
        book.setId("testIban");
        book.setBeneficiaryLastname("testIntitule");
        book.setAccountId("testIban");
        book.setIban("testIban");
        book.setScheme(Utils.BBAN);
        book.setAccountNumber("testNumero");
        book.setBalance(new BigDecimal("5"));
        book.setBalanceCurrency(String.valueOf(1));
        FindCompteType compteType = FindCompteType.findLibelleById("COMPTE A VUE");
        book.setType(compteType.getType());
        book.setTypeId(compteType.getTypeId());
        book.setSubtypeId(compteType.getId());
        book.setSubtype(compteType.getLibelle());
        TransactionAccount account = paymentOrder.getCreditAccount();
        if (account != null) {
            account.setId(book.getId());
            account.setIban(book.getIban());
            if (book.getType() != null) {
                account.setType(book.getType());
                account.setTypeId(book.getTypeId());
                account.setSubtypeId(book.getSubtypeId());
                account.setSubtype(book.getSubtype());
            }
            book.setBeneficiaryLastname("lastname");
            if (book.getIban().equals(book.getId())) {
                if (book.getBeneficiaryLastname() != null) {
                    account.setBeneficiaryLastName(book.getBeneficiaryLastname());
                    account.setAccountLabel(book.getBeneficiaryLastname());
                }
            }
            book.setAccountNumber("00001");
            if (book.getAccountNumber() != null) {
                account.setAccountNumber(book.getAccountNumber());
            }
            book.setBalance(new BigDecimal("100"));
            book.setBalanceCurrency("EUR");
            if (book.getBalance() != null) {
                account.setAccountBalance(book.getBalance());
                account.setAccountBalanceCurrency(book.getBalanceCurrency());
            }
        }
        paymentOrder.setCreditAccount(account);
        assertThat(ordreDetailToPaymentOrderMapper.map(paymentOrder, book))
                .isEqualTo(paymentOrder);
    }

    @Test
    public void map_PaymentOrder() {
        PaymentOrder input = new PaymentOrder();
        input.setId("setId");
        input.setStatus("setStatut");
        input.setCreditAccount(new TransactionAccount());
        input.setDebitAccount(new TransactionAccount());
        input.setTransactionInfo(new TransactionInfo());
        assertThat(ordreDetailToPaymentOrderMapper.map(input))
                .isEqualTo(new Payment(input.getId(), input.getStatus(), input.getTransactionInfo().getExecutionDateTime()));
    }

    @Test
    public void map_SignTransaction() {
        SignTransaction signTransaction = new SignTransaction();
        signTransaction.setEncryptedPassword("tUqwGyTqR83hI1kw9h6qaUCkSY1PI4kNdB3JeXfdlZrxpJINQ0LsAY39cUUCLOQ6GxsolIn2dRYxZ7C0IejbUZ8cbcJsqCDIPZbHtTGSa/gpw4HG058hoy6xTj97G8MR2zYzMhBMy45vpOJOUz1fbKtexb0vWcZseGAtoLEghZcjiI+TJCoO8N7jHuNAQopNFbALv3ZG5c5OzcIcZy2FJLYYyTiSn/ZB5xLA5TVnWLAIgPv+QmPy5J9KcZFkRBxBQ8y05QM+JKuoz0lu2Iw35Q+Xk+KFMk26BvvmgIC0ll0OJ81j7ozLBMvUPhYacxjVtbq0Ou/WgizSW/k2LlGWQ==");
        signTransaction.setKeyboardIdentifier("keyboard147825944027526B9C1592E1F1CC92A9C42E8F3258CD1");
        ArrayList<String> strings = new ArrayList<>();
        strings.add("WBSCT3_O_58501245c5438a1c_0");
        signTransaction.setTransactionIds(strings);
        String login = "toto";
        SignatureKBV expected = new SignatureKBV(login, signTransaction.getKeyboardIdentifier(), signTransaction.getEncryptedPassword());
        assertThat(ordreDetailToPaymentOrderMapper.map(signTransaction, login).getCodeClavierVirtuel()).isEqualTo(expected.getCodeClavierVirtuel());
        assertThat(ordreDetailToPaymentOrderMapper.map(signTransaction, login).getIdClavierVirtuel()).isEqualTo(expected.getIdClavierVirtuel());
        assertThat(ordreDetailToPaymentOrderMapper.map(signTransaction, login).getLoginWebAbonne()).isEqualTo(expected.getLoginWebAbonne());
    }

}