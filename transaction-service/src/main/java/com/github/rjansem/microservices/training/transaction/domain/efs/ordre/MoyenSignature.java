package com.github.rjansem.microservices.training.transaction.domain.efs.ordre;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Bean représentant un moyen de signature
 *
 * @author aazzerrifi
 */
public class MoyenSignature {

    @JsonProperty("type")
    private String type;

    @JsonProperty("niveauSecurite")
    private Integer niveauSecurite;

    @JsonProperty("alarmes")
    private List<Alarme> alarmes = null;

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("niveauSecurite")
    public Integer getNiveauSecurite() {
        return niveauSecurite;
    }

    @JsonProperty("niveauSecurite")
    public void setNiveauSecurite(Integer niveauSecurite) {
        this.niveauSecurite = niveauSecurite;
    }

    @JsonProperty("alarmes")
    public List<Alarme> getAlarmes() {
        return alarmes;
    }

    @JsonProperty("alarmes")
    public void setAlarmes(List<Alarme> alarmes) {
        this.alarmes = alarmes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .append("niveauSecurite", niveauSecurite)
                .append("alarmes", alarmes)
                .toString();
    }
}
