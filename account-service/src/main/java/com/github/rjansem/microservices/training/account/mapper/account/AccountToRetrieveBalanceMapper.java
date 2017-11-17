package com.github.rjansem.microservices.training.account.mapper.account;

import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.domain.pbi.account.RetrieveBalance;

/**
 * Mapper transformant une {@link Account} en {@link RetrieveBalance}
 *
 * @author mbouhamyd
 */
public class AccountToRetrieveBalanceMapper {


    public RetrieveBalance map(Account input) {
        RetrieveBalance retrieve = new RetrieveBalance();
        retrieve.setType(input.getType());
        retrieve.setTypeId(input.getTypeId());
        retrieve.setSubtype(input.getSubtype());
        retrieve.setSubtypeId(input.getSubtypeId());
        retrieve.setId(input.getId());
        retrieve.setAcountNumber(input.getAccountNumber());
        retrieve.setIban(input.getId());
        retrieve.setBookedBalance(input.getBookedBalance());
        retrieve.setBookedBalanceDate(input.getBookedBalanceDate());
        retrieve.setBookedBalanceCurrency(input.getCurrency());
        if (input.getType().equals("Compte courant") && input.getCreditcardsTotalBalance() != null) {
            retrieve.setCreditcardTotalBalance(input.getCreditcardsTotalBalance());
            retrieve.setCreditcardTotalCurrency(input.getBookedBalanceCurrency());
            retrieve.setCreditcardTotalBalanceDate(input.getCreditcardsTotalDate());
        }
        if (!input.getCurrency().equals("EUR")) {
            retrieve.setCountervalueBalance(input.getCountervalueBalance());
            retrieve.setCountervalueCurrency(input.getCountervalueCurrency());
            retrieve.setCountervalueRate(input.getCountervalueRate());
            retrieve.setCountervalueDate(input.getCountervalueDate().toString());
        }
        return retrieve;
    }

}
