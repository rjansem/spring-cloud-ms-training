package com.github.rjansem.microservices.training.transaction.mapper;


import com.github.rjansem.microservices.training.transaction.mapper.commun.Utils;
import com.github.rjansem.microservices.training.commons.domain.TwoEfsToPibMapper;
import com.github.rjansem.microservices.training.commons.domain.utils.FindCompteType;
import com.github.rjansem.microservices.training.transaction.domain.efs.beneficiaires.Tier;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.CompteOrdre;
import com.github.rjansem.microservices.training.transaction.domain.pbi.AddressBook;
import com.github.rjansem.microservices.training.transaction.mapper.commun.Utils;

import java.math.BigDecimal;

/**
 * Mapper transformant des compte emetteur en compte to debit
 *
 * @author aazzerrifi
 */
public class BenefciairesToAddressBookMapper implements TwoEfsToPibMapper<Tier, CompteOrdre, AddressBook> {

    @Override
    public AddressBook map(Tier externe, CompteOrdre interne) {
        AddressBook addressBook = new AddressBook ( );
        return addressBook;
    }


    public AddressBook map(Tier externe) {
        AddressBook addressBook = new AddressBook ( );
        if (externe != null) {
            addressBook.setId (String.valueOf (externe.getId ( )));
            addressBook.setBeneficiaryLastname (externe.getLibelle ( ));
            addressBook.setAccountId (externe.getIban ( ));
            addressBook.setIban (externe.getIban ( ));
            addressBook.setScheme (Utils.IBAN);
        }
        return addressBook;
    }

    public AddressBook map(CompteOrdre interne) {
        AddressBook addressBook = new AddressBook ( );
        if (interne != null) {
            addressBook.setId (String.valueOf (interne.getIban ( )));
            addressBook.setBeneficiaryLastname (interne.getIntitule ( ));
            addressBook.setAccountId (interne.getIban ( ));
            addressBook.setIban (interne.getIban ( ));
            addressBook.setScheme (Utils.BBAN);
            addressBook.setAccountNumber (interne.getNumero ( ));
            if (interne.getSolde()!= null && !interne.getSolde().isEmpty()) {
                addressBook.setBalance (new BigDecimal (interne.getSolde ( )));
                addressBook.setBalanceCurrency (String.valueOf (interne.getDevise ( )));
            }
            if (interne.getType() != null) {
                FindCompteType compteType = FindCompteType.findLibelleById(interne.getType());
                addressBook.setType(compteType.getType());
                addressBook.setTypeId(compteType.getTypeId());
                addressBook.setSubtypeId(compteType.getId());
                addressBook.setSubtype(compteType.getLibelle());
            }
        }
        return addressBook;
    }
}
