package com.github.rjansem.microservices.training.account.mapper.account.investissement;

import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType;
import com.github.rjansem.microservices.training.account.domain.efs.investissement.AssuranceVie;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Mapper transformant un {@link AssuranceVie} en {@link Account}
 *
 * @author aazzerrifi
 */
public class AssuranceVieToAccountMapperTest {

    @Test
    public void map_shouldMapAllFields() {
        Account expected = new Account();
        expected.setType(CompteType.ASSURANCE_VIE.getLibelle());
        expected.setTypeId(CompteType.ASSURANCE_VIE.getId());
        expected.setAccountNumber("AV000001");
        expected.setId("AV000001");
        expected.setCompany("AXA");
        expected.setSubtypeId("Interne");
        AssuranceVie assuranceVie = new AssuranceVie();
        assuranceVie.setNumero("AV000001");
        assuranceVie.setIbanCompte("AV000001");
        assuranceVie.setType("Interne");
        assuranceVie.setNomAssureur("Axa");
        assertThat(new AssuranceVieToAccountMapper().map(assuranceVie)).isEqualToComparingFieldByField(expected);
    }
}