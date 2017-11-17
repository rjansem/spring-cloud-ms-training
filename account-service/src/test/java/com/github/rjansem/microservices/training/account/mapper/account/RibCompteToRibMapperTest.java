package com.github.rjansem.microservices.training.account.mapper.account;

import com.github.rjansem.microservices.training.account.domain.efs.compte.RibCompte;
import com.github.rjansem.microservices.training.account.domain.efs.compte.rib.Domiciliation;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Rib;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.StringJoiner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Mapper transformant un {@link RibCompte} en {@link Rib}.
 *
 * @author aazzerrifi
 * @see RibCompte
 * @see Rib
 */
public class RibCompteToRibMapperTest {

    @Test
    public void map_shouldMapAllFields() {
        Rib expected = new Rib();
        expected.setType("");
        expected.setTypeId("");
        expected.setSubtype("");
        expected.setSubtypeId("");
        expected.setId("FR7630788001002504780000341");
        expected.setAccountNumber("10106895245");
        expected.setIban("FR7630788001002504780000341");
        expected.setBic("NSMBFRPPXXX");
        expected.setBankName("Banque Neuflize OBC - 00100");
        expected.setBankAddress(new StringJoiner(StringUtils.SPACE) //TODO vérifier
                .add("3, avenue Hoche")
                .add("75410 Paris Cedex 08")
                .add("")
                .add("FRANCE")
                .toString());
        expected.setClientLastName("AMIOT FABRICE");
        RibCompte ribCompte = new RibCompte();
        ribCompte.setTitulaire("AMIOT FABRICE");
        Domiciliation domiciliation = new Domiciliation();
        domiciliation.setNom("Banque Neuflize OBC - 00100");
        domiciliation.setAdresse1("3, avenue Hoche");
        domiciliation.setAdresse2("75410 Paris Cedex 08");
        domiciliation.setAdresse3("");
        domiciliation.setPays("FRANCE");
        ribCompte.setDomiciliation(domiciliation);
        ribCompte.setBic("NSMBFRPPXXX");
        ribCompte.setIban("FR7630788001002504780000341");
        ribCompte.setNumeroCompte("10106895245");
        assertThat(new RibCompteToRibMapper().map(ribCompte)).isEqualToComparingFieldByField(expected);
    }

}