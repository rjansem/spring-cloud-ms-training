package com.github.rjansem.microservices.training.transaction.domain.efs.ordre;

import com.github.rjansem.microservices.training.commons.domain.EfsBean;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

/**
 * @author aazzerrifi
 */
public class EmissionOrdre implements EfsBean {

    private String idOrdre;

    public EmissionOrdre() {
        // for jackson
    }

    public EmissionOrdre(String idOrdre) {
        this.idOrdre = idOrdre;
    }

    public String getIdOrdre() {
        return idOrdre;
    }

    public void setIdOrdre(String idOrdre) {
        this.idOrdre = idOrdre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmissionOrdre)) {
            return false;
        }
        EmissionOrdre that = (EmissionOrdre) o;
        return Objects.equals (idOrdre, that.idOrdre);
    }

    @Override
    public int hashCode() {
        return Objects.hash (idOrdre);
    }

    @Override
    public String toString() {
        return new ToStringBuilder (this)
                .append ("idOrdre", idOrdre)
                .toString ( );
    }
}
