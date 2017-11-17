package com.github.rjansem.microservices.training.transaction.mapper;

import com.github.rjansem.microservices.training.commons.domain.EfsToPibMapper;
import com.github.rjansem.microservices.training.commons.domain.utils.FindCompteType;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.CompteOrdre;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.CompteSubType;
import com.github.rjansem.microservices.training.transaction.domain.pbi.account.Account;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * Mapper transformant un compte emetteur en compte to debit
 *
 * @author mbouhamyd
 */
public class CompteOrdreToAccountMapper implements EfsToPibMapper<CompteOrdre, Account> {
    @Override
    public Account map(CompteOrdre input) {
        Account accountToDebit = new Account ( );
        if (input.getType() != null) {
            FindCompteType compteType = FindCompteType.findLibelleById(input.getType());
                accountToDebit.setType(compteType.getType());
                accountToDebit.setTypeId(compteType.getTypeId());
                accountToDebit.setSubtypeId(compteType.getId());
                accountToDebit.setSubtype(compteType.getLibelle());
        }
        accountToDebit.setId (input.getIban ( ));
        accountToDebit.setAccountNumber(input.getNumero());
        accountToDebit.setAccountLabel (input.getIntitule ( ));
        if (StringUtils.isNotBlank(input.getSolde())) {
            BigDecimal balance = new BigDecimal(input.getSolde());
            accountToDebit.setBalance(balance);
        }
        accountToDebit.setCurrency (String.valueOf (input.getDevise ( )));
            return accountToDebit;
    }
}
