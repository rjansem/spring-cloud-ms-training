package com.github.rjansem.microservices.training.account.mapper.account.chekingAndSaving;

import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteCourant;
import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests associés au mapping d'un {@link CompteCourant} en {@link Account}
 *
 * @author jntakpe
 */
public class CompteCourantToAccountMapperTest {

    @Test
    public void map_shouldMapAllFields() {
        Account expected = new Account();
        expected.setType(CompteType.COURANT.getLibelle());
        expected.setAccountNumber("CC000001");
        expected.setSubtype ("COMPTE A VUE");
        expected.setId("someIban");
        expected.setIban("someIban");
        expected.setCurrency ("10");
        expected.setTypeId ("DAV");
        expected.setSubtypeId ("CAV");
        expected.setBookedBalance (new BigDecimal ("200000.50"));
        CompteCourant compteCourant = new CompteCourant ("CC000001", "COMPTE A VUE", "someIntitule", "200000.50", 10, "someIban");
        compteCourant.setIban("someIban");
        assertThat(new CompteCourantToAccountMapper().map(compteCourant)).isEqualToComparingFieldByField(expected);
    }

}
