package com.github.rjansem.microservices.training.account.mapper.account.investissement;

import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType;
import com.github.rjansem.microservices.training.account.domain.efs.investissement.PortefeuilleTitres;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Mapper transformant un {@link PortefeuilleTitres} en {@link Account}
 *
 * @author aazzerrifi
 */
public class PortefeuillesTitresToAccountMapperTest {
    @Test
    public void map_shouldMapAllFields() {
        Account expected = new Account();
        expected.setType(CompteType.PORTEFEUILLE_TITRES.getLibelle());
        expected.setTypeId(CompteType.PORTEFEUILLE_TITRES.getId());
        expected.setAccountNumber("PT000001");
        expected.setId("PT000001");
        expected.setSubtype("COMPTE TITRE PEA GERE");
        expected.setSubtypeId("PEA_GERE");
        expected.setIban("CC000001");
        PortefeuilleTitres portefeuilleTitres = new PortefeuilleTitres();
        portefeuilleTitres.setType("PEA");
        portefeuilleTitres.setTypeGestion("O");
        portefeuilleTitres.setNumero("PT000001");
        portefeuilleTitres.setIbanCompte("CC000001");
        assertThat(new PortefeuillesTitresToAccountMapper().map(portefeuilleTitres)).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void map_shouldMapAllFields_2() {
        Account expected = new Account();
        expected.setType(CompteType.PORTEFEUILLE_TITRES.getLibelle());
        expected.setTypeId(CompteType.PORTEFEUILLE_TITRES.getId());
        expected.setAccountNumber("PT000001");
        expected.setId("PT000001");
        expected.setSubtype("COMPTE TITRE PEA");
        expected.setSubtypeId("PEA_NON_GERE");
        expected.setIban("CC000001");
        PortefeuilleTitres portefeuilleTitres = new PortefeuilleTitres();
        portefeuilleTitres.setType("PEA");
        portefeuilleTitres.setTypeGestion("N");
        portefeuilleTitres.setNumero("PT000001");
        portefeuilleTitres.setIbanCompte("CC000001");
        assertThat(new PortefeuillesTitresToAccountMapper().map(portefeuilleTitres)).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void map_shouldMapAllFields_3() {
        Account expected = new Account();
        expected.setType(CompteType.PORTEFEUILLE_TITRES.getLibelle());
        expected.setTypeId(CompteType.PORTEFEUILLE_TITRES.getId());
        expected.setAccountNumber("PT000001");
        expected.setId("PT000001");
        expected.setSubtype("COMPTE TITRE GERE");
        expected.setSubtypeId("NON_PEA_GERE");
        expected.setIban("CC000001");
        PortefeuilleTitres portefeuilleTitres = new PortefeuilleTitres();
        portefeuilleTitres.setType("NONPEA");
        portefeuilleTitres.setTypeGestion("O");
        portefeuilleTitres.setNumero("PT000001");
        portefeuilleTitres.setIbanCompte("CC000001");
        assertThat(new PortefeuillesTitresToAccountMapper().map(portefeuilleTitres)).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void map_shouldMapAllFields_4() {
        Account expected = new Account();
        expected.setType(CompteType.PORTEFEUILLE_TITRES.getLibelle());
        expected.setTypeId(CompteType.PORTEFEUILLE_TITRES.getId());
        expected.setAccountNumber("PT000001");
        expected.setId("PT000001");
        expected.setSubtype("COMPTE TITRE");
        expected.setSubtypeId("NON_PEA_NON_GERE");
        expected.setIban("CC000001");
        PortefeuilleTitres portefeuilleTitres = new PortefeuilleTitres();
        portefeuilleTitres.setType("NONPEA");
        portefeuilleTitres.setTypeGestion("N");
        portefeuilleTitres.setNumero("PT000001");
        portefeuilleTitres.setIbanCompte("CC000001");
        assertThat(new PortefeuillesTitresToAccountMapper().map(portefeuilleTitres)).isEqualToComparingFieldByField(expected);
    }
}