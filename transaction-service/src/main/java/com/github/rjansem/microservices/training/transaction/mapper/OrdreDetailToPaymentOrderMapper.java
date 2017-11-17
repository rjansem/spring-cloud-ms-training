package com.github.rjansem.microservices.training.transaction.mapper;


import com.github.rjansem.microservices.training.transaction.mapper.commun.CompteSubType;
import com.github.rjansem.microservices.training.commons.domain.EfsToPibMapper;
import com.github.rjansem.microservices.training.commons.domain.utils.DateUtils;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.Beneficiaire;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.CompteOrdre;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.OrdreDetail;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.SignatureKBV;
import com.github.rjansem.microservices.training.transaction.domain.pbi.AddressBook;
import com.github.rjansem.microservices.training.transaction.domain.pbi.PaymentOrder;
import com.github.rjansem.microservices.training.transaction.domain.pbi.TransactionInfo;
import com.github.rjansem.microservices.training.transaction.domain.pbi.account.TransactionAccount;
import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.Payment;
import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.SignTransaction;
import com.github.rjansem.microservices.training.transaction.mapper.commun.CompteSubType;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * Mapper transformant un ordre en transaction
 *
 * @author aazzerrifi
 */
public class OrdreDetailToPaymentOrderMapper implements EfsToPibMapper<OrdreDetail, PaymentOrder>{

    @Override
    public PaymentOrder map(OrdreDetail input){
        PaymentOrder paymentOrder = new PaymentOrder();
        if(input != null){
            paymentOrder.setId(input.getId());
            paymentOrder.setStatus(input.getStatut());
            paymentOrder.setDebitAccount(getDebitAccount(input.getCompteEmetteur()));
            paymentOrder.setCreditAccount(getCreditAccount(input.getBeneficiaires().stream().findFirst().get()));
            TransactionInfo transactionInfo = getTransactionInfo(input);
            paymentOrder.setTransactionInfo(transactionInfo);
        }
        return paymentOrder;
    }

    public PaymentOrder map(PaymentOrder paymentOrder, AddressBook book) {
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
            if (book.getIban().equals(book.getId())) {
                if (book.getBeneficiaryLastname() != null) {
                    account.setBeneficiaryLastName(book.getBeneficiaryLastname());
                    account.setAccountLabel(book.getBeneficiaryLastname());
                }
            }
            if (book.getAccountNumber() != null) {
                account.setAccountNumber(book.getAccountNumber());
            }
            if (book.getBalance() != null) {
                account.setAccountBalance(book.getBalance());
                account.setAccountBalanceCurrency(book.getBalanceCurrency());
            }
        }
        paymentOrder.setCreditAccount(account);
        return paymentOrder;
    }

    public Payment map(PaymentOrder input){
        return new Payment(input.getId(), input.getStatus(), input.getTransactionInfo().getExecutionDateTime());
    }

    public SignatureKBV map(SignTransaction signTransaction, String login){
        return new SignatureKBV(login, signTransaction.getKeyboardIdentifier(), signTransaction.getEncryptedPassword());
    }

    private TransactionAccount getDebitAccount(CompteOrdre input){
        TransactionAccount account = new TransactionAccount();
        if(input != null){
            account.setId(String.valueOf(input.getIban()));
            account.setIban(input.getIban());
            if(input.getType() != null){
                account.setType(CompteSubType.findLibelleById(input.getType()).getType());
                account.setTypeId(CompteSubType.findLibelleById(input.getType()).getTypeId());
                account.setSubtypeId(CompteSubType.findLibelleById(input.getType()).getId());
                account.setSubtype(input.getType());
            }
            if(input.getIntitule() != null){
                account.setAccountLabel(input.getIntitule());
            }
            if(input.getNumero() != null){
                account.setAccountNumber(input.getNumero());
            }
            if(StringUtils.isNotBlank(input.getSolde())){
                account.setAccountBalance(new BigDecimal(input.getSolde()));
                account.setAccountBalanceCurrency(String.valueOf(input.getDevise()));
            }
        }
        return account;
    }

    private TransactionAccount getCreditAccount(Beneficiaire input){
        TransactionAccount account = new TransactionAccount();
        if(input != null){
            account.setId(input.getIban());
            account.setIban(input.getIban());
            if(input.getLibelle() != null){
                account.setBeneficiaryLastName(input.getLibelle());
            }
        }
        return account;
    }

    private TransactionInfo getTransactionInfo(OrdreDetail input){
        TransactionInfo transactionInfo = new TransactionInfo();
        if(input.getMontant() != null){
            transactionInfo.setPaymentMode(input.getBeneficiaires().get(0).getReference());
            //TODO Pas possible de gérer : UrgentTransfer
            //transactionInfo.setUrgentTransfer (false);
            transactionInfo.setMemo(input.getBeneficiaires().get(0).getMotif());
            transactionInfo.setInstructedAmount(new BigDecimal(input.getMontant()));
            transactionInfo.setInstructedCurrency(input.getCodeDevise());
            transactionInfo.setExecutionDateTime(DateUtils.convertEfsDateToPibDate(input.getDateExecutionSouhaitee()));
        }
        return transactionInfo;
    }


}
