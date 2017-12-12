package com.github.rjansem.microservices.training.account.mapper.account.investissement;

import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType;
import com.github.rjansem.microservices.training.account.domain.efs.investissement.AssuranceVie;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;

/**
 * Mapper transformant un {@link AssuranceVie} en {@link Account}
 *
 * @author rjansem
 */
public class AssuranceVieToAccountMapper extends InvestissementToAccountMapper<AssuranceVie> {

    @Override
    protected Account getAccount(AssuranceVie input) {
        Account account = new Account();
        account.setTypeId(CompteType.ASSURANCE_VIE.getId());
        account.setType(CompteType.ASSURANCE_VIE.getLibelle());
        account.setCompany(input.getNomAssureur().toUpperCase());
        account.setSubtypeId(input.getType());
        account.setSubtype(input.getIntitule());
        return account;
    }
}

