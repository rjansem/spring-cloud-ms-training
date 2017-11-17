package com.github.rjansem.microservices.training.transaction.mapper;

import com.github.rjansem.microservices.training.commons.domain.utils.FindCompteType;
import com.github.rjansem.microservices.training.transaction.domain.efs.beneficiaires.Tier;
import com.github.rjansem.microservices.training.transaction.domain.efs.ordre.CompteOrdre;
import com.github.rjansem.microservices.training.transaction.domain.pbi.AddressBook;
import com.github.rjansem.microservices.training.transaction.mapper.commun.Utils;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author aazzerrifi
 */
public class BenefciairesToAddressBookMapperTest {

    private BenefciairesToAddressBookMapper benefciairesToAddressBookMapper = new BenefciairesToAddressBookMapper ( );

    @Test
    public void map() throws Exception {
        assertThat (benefciairesToAddressBookMapper.map (new Tier ( ), new CompteOrdre ( )))
                .isEqualTo (new AddressBook ( ));
    }

    @Test
    public void map_Tier_To_AddressBook() throws Exception {
        Tier tier = new Tier ( );
        tier.setId (2);
        tier.setLibelle ("testLib");
        tier.setIban ("testIban");
        AddressBook addressBook = new AddressBook ( );
        addressBook.setId (String.valueOf (2));
        addressBook.setBeneficiaryLastname ("testLib");
        addressBook.setAccountId ("testIban");
        addressBook.setIban ("testIban");
        addressBook.setScheme (Utils.IBAN);
        assertThat (benefciairesToAddressBookMapper.map (tier))
                .isEqualTo (addressBook);
    }

    @Test
    public void map_CompteOrdre_To_AddressBook() throws Exception {
        CompteOrdre compteOrdre = new CompteOrdre ();
        compteOrdre.setIban ( "testIban");
        compteOrdre.setIntitule ("testIntitule" );
        compteOrdre.setNumero ("testNumero" );
        compteOrdre.setSolde ( "5");
        compteOrdre.setDevise (1 );
        compteOrdre.setType("COMPTE A VUE");

        AddressBook addressBook = new AddressBook ( );
        addressBook.setId ("testIban");
        addressBook.setBeneficiaryLastname ("testIntitule");
        addressBook.setAccountId ("testIban");
        addressBook.setIban ("testIban");
        addressBook.setScheme (Utils.BBAN);
        addressBook.setAccountNumber ("testNumero");
        addressBook.setBalance (new BigDecimal ("5"));
        addressBook.setBalanceCurrency (String.valueOf (1));
        FindCompteType compteType = FindCompteType.findLibelleById(compteOrdre.getType());
        addressBook.setType(compteType.getType());
        addressBook.setTypeId(compteType.getTypeId());
        addressBook.setSubtypeId(compteType.getId());
        addressBook.setSubtype(compteType.getLibelle());
        assertThat (benefciairesToAddressBookMapper.map (compteOrdre))
                .isEqualTo (addressBook);
    }
}