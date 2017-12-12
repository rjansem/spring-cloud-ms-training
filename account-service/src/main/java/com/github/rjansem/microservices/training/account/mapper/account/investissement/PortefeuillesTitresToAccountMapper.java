package com.github.rjansem.microservices.training.account.mapper.account.investissement;

import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType;
import com.github.rjansem.microservices.training.account.domain.efs.investissement.PortefeuilleTitres;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.commons.domain.utils.FindCompteType;

/**
 * Mapper transformant un {@link PortefeuilleTitres} en {@link Account}
 *
 * @author rjansem
 */
public class PortefeuillesTitresToAccountMapper extends InvestissementToAccountMapper<PortefeuilleTitres> {

    @Override
    protected Account getAccount(PortefeuilleTitres input) {
        Account account = new Account();
        account.setTypeId(CompteType.PORTEFEUILLE_TITRES.getId());
        account.setType(CompteType.PORTEFEUILLE_TITRES.getLibelle());
        if (input.getIbanCompte() != null) {
            account.setIban(input.getIbanCompte());
        }
        generateSubTypeAccount(account, input.getTypeGestion(), input.getType());
        return account;
    }

    private Account generateSubTypeAccount(Account account, String typeGestion, String type) {
        if (type.equals("PEA")) {
            if (typeGestion.equals("O")) {
                account.setSubtypeId(FindCompteType.PORTEFEUILLE_TITRES_PEA_GERE.getId());
                account.setSubtype(FindCompteType.PORTEFEUILLE_TITRES_PEA_GERE.getLibelle());
            } else {
                account.setSubtypeId(FindCompteType.PORTEFEUILLE_TITRES_PEA_NON_GERE.getId());
                account.setSubtype(FindCompteType.PORTEFEUILLE_TITRES_PEA_NON_GERE.getLibelle());
            }
        } else {
            if (typeGestion.equals("O")) {
                account.setSubtypeId(FindCompteType.PORTEFEUILLE_TITRES_NON_PEA_GERE.getId());
                account.setSubtype(FindCompteType.PORTEFEUILLE_TITRES_NON_PEA_GERE.getLibelle());
            } else {
                account.setSubtypeId(FindCompteType.PORTEFEUILLE_TITRES_NON_PEA_NON_GERE.getId());
                account.setSubtype(FindCompteType.PORTEFEUILLE_TITRES_NON_PEA_NON_GERE.getLibelle());
            }
        }
        return account;
    }
}
