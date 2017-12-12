package com.github.rjansem.microservices.training.apisecurity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Objects;

/**
 * Énumération des droits des utilisateurs
 *
 * @author rjansem
 */
public enum Authority {

    @JsonProperty("administration-comptes")
    ADMINISTRATION_COMPTES,

    @JsonProperty("transactions-simples")
    TRANSACTION_SIMPLES,

    @JsonProperty("WebankEdito")
    WEBANK_EDITO,

    @JsonProperty("environnement")
    ENVIRONNEMENT,

    @JsonProperty("paywebc@rd")
    PAYWEBCARD,

    @JsonProperty("Titres Bourse")
    TITRES_BOURSE,

    @JsonProperty("commande")
    COMMANDE,

    @JsonProperty("administration-assurance-vie")
    ADMINISTRATION_ASSURANCE_VIE,

    @JsonProperty("telechargement")
    TELECHARGEMENT,

    @JsonProperty("Coffre-fort")
    COFFRE_FORT,

    @JsonProperty("GACCES01DAAP001")
    GACCES01DAAP001,

    @JsonProperty("GACCES01DAAP002")
    GACCES01DAAP002,

    @JsonProperty("GACCES01DAAP003")
    GACCES01DAAP003,

    @JsonProperty("GACCES01DAAP004")
    GACCES01DAAP004,

    @JsonProperty("GACCES01DAAP006")
    GACCES01DAAP006,

    @JsonProperty("FRPRAC01DAFR001")
    FRPRAC01DAFR001,

    @JsonProperty("transactions")
    TRANSACTION,

    @JsonProperty("consultations")
    CONSULTATION,

    @JsonProperty("GACCES01DAAP005")
    GACCES01DAAP005,

    @JsonProperty("INCONNUE")
    INCONNUE;

    static Authority relaxedValueOf(String value) {
        Objects.requireNonNull(value);
        return Arrays.stream(Authority.values())
                .filter(a -> value.toUpperCase().startsWith(a.name()))
                .findAny()
                //.orElseThrow(() -> new IllegalStateException(String.format("Impossible de mapper %s avec %s", value, Authority.class)));
                .orElse(Authority.INCONNUE);
    }

}
