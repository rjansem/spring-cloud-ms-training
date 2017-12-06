package com.github.rjansem.microservices.training.account.mapper.account.chekingAndSaving;

import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteCourant;
import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.commons.domain.utils.DateUtils;

import java.math.BigDecimal;

/**
 * Mapper transformant un {@link CompteCourant} en {@link Account}
 *
 * @author rjansem
 * @author azzerrifi
 * @see CompteToAccountMapper
 */
public class CompteCourantToAccountMapper extends CompteToAccountMapper<CompteCourant> {

    @Override
    protected Account getAccount(CompteCourant input) {
        Account account = new Account();
        if(input.getType()!=null) {
            account.setTypeId(CompteType.COURANT.getId());
            account.setType(CompteType.COURANT.getLibelle());
        }
        if (input.getEncoursCartesBancaires() != null && !input.getEncoursCartesBancaires().isEmpty()) {
                account.setCreditcardsTotalBalance(new BigDecimal(input.getEncoursCartesBancaires()));
                account.setCreditcardsTotalDate(DateUtils.convertEfsDateToPibDate(input.getDateEncoursCartesBancaires()));
        }

        return account;
    }
}
