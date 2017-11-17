package com.github.rjansem.microservices.training.transaction.mapper.commun;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by aazzerrifi on 06/03/2017.
 */
public class CompteSubTypeTest {
    @Test
    public void findLibelleById_Courant() throws Exception {
        CompteSubType libelleById = CompteSubType.findLibelleById("COMPTE A VUE");
        assertThat(CompteSubType.findLibelleById("COMPTE A VUE")).isEqualTo(libelleById);

        CompteSubType libelleById2 = CompteSubType.findLibelleById("COMPTE A VUE REMUN.");
        assertThat(CompteSubType.findLibelleById("COMPTE A VUE REMUN.").getId()).isEqualTo(libelleById2.getId());

        CompteSubType libelleById3 = CompteSubType.findLibelleById("COMPTE NANTI GERE");
        assertThat(CompteSubType.findLibelleById("COMPTE NANTI GERE").getType()).isEqualTo(libelleById3.getType());

        CompteSubType libelleById4 = CompteSubType.findLibelleById("COMPTE GERE");
        assertThat(CompteSubType.findLibelleById("COMPTE GERE").getTypeId()).isEqualTo(libelleById4.getTypeId());

        CompteSubType libelleById5 = CompteSubType.findLibelleById("COMPTE NANTI");
        assertThat(CompteSubType.findLibelleById("COMPTE NANTI")).isEqualTo(libelleById5);
    }

    @Test
    public void getLibelle() throws Exception {
    }

    @Test
    public void getId() throws Exception {
    }

    @Test
    public void getType() throws Exception {
    }

    @Test
    public void getTypeId() throws Exception {
    }

}