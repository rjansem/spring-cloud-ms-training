package com.github.rjansem.microservices.training.transaction.mapper;


import com.github.rjansem.microservices.training.commons.domain.utils.DateUtils;
import com.github.rjansem.microservices.training.transaction.domain.efs.post.PostTransaction;
import com.github.rjansem.microservices.training.transaction.domain.pbi.PaymentOrder;
import com.github.rjansem.microservices.training.transaction.domain.pbi.TransactionInfo;
import com.github.rjansem.microservices.training.transaction.domain.pbi.account.TransactionAccount;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.github.rjansem.microservices.training.commons.domain.utils.DateUtils.EFS_POST_DATE_FORMAT;
import static com.github.rjansem.microservices.training.transaction.api.ApiConstants.CODE_APPLICATION;


/**
 * Mapper transformant un ordre en transaction
 *
 * @author aazzerrifi
 */
public class PostPaymentOrderToPostTransactionMapper {

    public PostTransaction map(PaymentOrder input, String login) {
        TransactionInfo transactionInfo = input.getTransactionInfo();
        TransactionAccount creditAccount = input.getCreditAccount();
        if (creditAccount.getId().equals(creditAccount.getIban())) {
            return getPostTransactionInt(input, login, transactionInfo);
        }
        return getPostTransactionExt(input, login, transactionInfo);
    }

    private PostTransaction getPostTransactionInt(PaymentOrder input, String login, TransactionInfo transactionInfo) {
        String date = DateUtils.convertPbiDateTimeToEfsPostDateString(transactionInfo.getExecutionDateTime());
        LocalDate efsdate= StringUtils.isBlank(date) ? null : LocalDate.parse(date, DateTimeFormatter.ofPattern(EFS_POST_DATE_FORMAT));
        LocalDate now= LocalDate.now();
        if(efsdate.isBefore(now)){
            date=LocalDate.parse(LocalDate.now().toString()).format(DateTimeFormatter.ofPattern(EFS_POST_DATE_FORMAT));
        }
        PostTransaction postTransaction = new PostTransaction(
                login, "2",
                CODE_APPLICATION,
                input.getDebitAccount().getId(),
                input.getCreditAccount().getIban(),
                transactionInfo.getInstructedAmount().toString(),
                date,
                transactionInfo.getMemo(),
                transactionInfo.getPaymentMode(),
                transactionInfo.getInstructedCurrency());
        return postTransaction;
    }

    private PostTransaction getPostTransactionExt(PaymentOrder input, String login, TransactionInfo transactionInfo) {
        String date = DateUtils.convertPbiDateTimeToEfsPostDateString(transactionInfo.getExecutionDateTime());
        LocalDate efsdate= StringUtils.isBlank(date) ? null : LocalDate.parse(date, DateTimeFormatter.ofPattern(EFS_POST_DATE_FORMAT));
        LocalDate now= LocalDate.now();
        if(efsdate.isBefore(now)){
            date=LocalDate.parse(LocalDate.now().toString()).format(DateTimeFormatter.ofPattern(EFS_POST_DATE_FORMAT));
        }
        PostTransaction postTransaction = new PostTransaction();
        postTransaction.setLoginWebAbonne(login);
        postTransaction.setNbDecimales("2");
        postTransaction.setCodeApplication(CODE_APPLICATION);
        postTransaction.setIbanCompteEmetteur(input.getDebitAccount().getId());
        postTransaction.setIdTiersBeneficiaire(input.getCreditAccount().getId());
        postTransaction.setMontant(transactionInfo.getInstructedAmount().toString());
        postTransaction.setDateExecutionSouhaitee(date);
        postTransaction.setMotif(transactionInfo.getMemo());
        postTransaction.setReference(transactionInfo.getPaymentMode());
        postTransaction.setDevise(transactionInfo.getInstructedCurrency());
        return postTransaction;
    }
}
