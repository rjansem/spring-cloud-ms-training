
package com.github.rjansem.microservices.training.account.mapper.account.chekingAndSaving;

import com.github.rjansem.microservices.training.account.domain.efs.compte.DepotATerme;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import org.junit.Test;

import static com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType.DEPOT_A_TERME;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Mapper transformant un {@link DepotATerme} en {@link Account}
 *
 * @author aazzerrifi
 * @see DepotsATermeToAccountMapper
 */
public class DepotsATermeToAccountMapperTest {
    @Test
    public void map_shouldMapAllFields() {
        Account expected = new Account();
        expected.setType(DEPOT_A_TERME.getLibelle());
        expected.setAccountNumber("CC000001");
        expected.setTypeId(DEPOT_A_TERME.getId());
        expected.setId("someId");
        DepotATerme depotATerme = new DepotATerme();
        depotATerme.setNumero("someId");
        depotATerme.setNumeroCompte("CC000001");
        assertThat(new DepotsATermeToAccountMapper().map(depotATerme)).isEqualToComparingFieldByField(expected);
    }
}