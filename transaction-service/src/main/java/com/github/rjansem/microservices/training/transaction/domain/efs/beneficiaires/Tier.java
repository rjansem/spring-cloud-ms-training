package com.github.rjansem.microservices.training.transaction.domain.efs.beneficiaires;

import com.github.rjansem.microservices.training.commons.domain.EfsBean;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Objects;

/**
 * Created by aazzerrifi on 26/12/2016.
 */
public class Tier implements EfsBean {
    private int id;

    private String libelle;

    private String iban;

    private List<Libelle> listes;

    public Tier() {
        // for jackson
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public List<Libelle> getListes() {
        return listes;
    }

    public void setListes(List<Libelle> listes) {
        this.listes = listes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tier)) {
            return false;
        }
        Tier tier = (Tier) o;
        return id == tier.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash (id);
    }

    @Override
    public String toString() {
        return new ToStringBuilder (this)
                .append ("id", id)
                .append ("libelle", libelle)
                .append ("iban", iban)
                .toString ( );
    }
}
