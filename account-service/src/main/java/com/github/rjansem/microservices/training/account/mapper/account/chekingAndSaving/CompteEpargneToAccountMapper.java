package com.github.rjansem.microservices.training.account.mapper.account.chekingAndSaving;

import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteEpargne;
import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;

/**
 * Mapper transformant un {@link CompteEpargne} en {@link Account}
 *
 * @author rjansem
 * @see CompteToAccountMapper
 */
public class CompteEpargneToAccountMapper extends CompteToAccountMapper<CompteEpargne> {

    @Override
    protected Account getAccount(CompteEpargne input) {
        Account account = new Account();
        account.setTypeId(CompteType.EPARGNE.getId());
        account.setType(CompteType.EPARGNE.getLibelle());
        return account;
    }
}
