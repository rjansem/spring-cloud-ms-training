package com.github.rjansem.microservices.training.transaction.domain.efs.ordre;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.rjansem.microservices.training.commons.domain.EfsBean;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Bean représentant une Alarme
 *
 * @author aazzerrifi
 */
@JsonPropertyOrder({
        "code",
        "libelle"
})
public class Alarme implements EfsBean {

    @JsonProperty("code")
    private String code;
    @JsonProperty("libelle")
    private String libelle;

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("libelle")
    public String getLibelle() {
        return libelle;
    }

    @JsonProperty("libelle")
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", code)
                .append("libelle", libelle)
                .toString();
    }
}

