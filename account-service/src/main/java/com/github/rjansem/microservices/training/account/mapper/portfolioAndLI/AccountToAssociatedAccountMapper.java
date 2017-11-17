package com.github.rjansem.microservices.training.account.mapper.portfolioAndLI;

import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.domain.pbi.portfolioAndLI.AssociatedAccount;

/**
 * mapper qui transforme un account en  associededAccount (portefeuilles ou assurance)
 * *
 *
 * @author mbouhamyd
 */
public class AccountToAssociatedAccountMapper {

    public AssociatedAccount map(Account account) {
        AssociatedAccount associetedAccount = new AssociatedAccount();
        if(account.getId()!=null) {
            associetedAccount.setType(account.getType());
            associetedAccount.setTypeId(account.getTypeId());
            associetedAccount.setSubtype(account.getSubtype());
            associetedAccount.setSubtypeId(account.getSubtypeId());
            associetedAccount.setAccountNumber(account.getAccountNumber());
            associetedAccount.setId(account.getId());
            if (account.getBookedBalance() != null) {
                associetedAccount.setBookedBalance(account.getBookedBalance());
            }
            associetedAccount.setAccountCurrency(account.getCurrency());
            associetedAccount.setBookedBalanceDate(account.getBookedBalanceDate().toString());
        }
        return associetedAccount;
    }
}
