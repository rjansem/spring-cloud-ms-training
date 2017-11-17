package com.github.rjansem.microservices.training.account.mapper.account.chekingAndSaving;

import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteEpargne;
import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Mapper transformant un {@link CompteEpargne} en {@link Account}
 *
 * @author aazzerrifi
 * @see CompteToAccountMapper
 */
public class CompteEpargneToAccountMapperTest {

    @Test
    public void map_shouldMapAllFields() {
        Account expected = new Account();
        expected.setType(CompteType.EPARGNE.getLibelle());
        expected.setAccountNumber("CC000001");
        expected.setSubtype ("PEA");
        expected.setId("someIban");
        expected.setIban("someIban");
        expected.setCurrency ("10");
        expected.setTypeId ("Epargne");
        expected.setSubtypeId ("PEA");
        expected.setBookedBalance (new BigDecimal ("200000.50"));
        CompteEpargne compteEpargne = new CompteEpargne ("CC000001", "PEA", "someIntitule", "200000.50", 10, "someIban");
        assertThat(new CompteEpargneToAccountMapper().map(compteEpargne)).isEqualToComparingFieldByField(expected);
    }

}