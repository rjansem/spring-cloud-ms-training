package com.github.rjansem.microservices.training.commons.domain.utils;

import java.util.Arrays;

/**
 * Énumération des types de comptes
 *
 * @author bouhamyd
 */
public enum FindProfilByType {

    EIF("EIF","P"),
    EIH("EIH","P"),
    SCHP("SCHP","P"),
    PARF("PARF","P"),
    PARH("PARH","P"),
    STE("STE","E"),
    STFI("STFI ","E"),
    INST("INST","E"),
    EIRL("EIRL","E"),
    BANQ("BANQ","E"),
    SYNC("SYNC","E"),
    JOEP("JOEP","P"),
    JOTP("JOTP","P"),
    JOTM("JOTM","E"),
    INDI("INDI","P"),
    BQNS("BQNS","E"),
    BQPL("BQPL","E"),
    TYPE_INCONNU("XX", "XX");;

    private String type;

    private String profil;

    FindProfilByType( String type,String profil) {
        this.type = type;
        this.profil = profil;

    }

    public static FindProfilByType findProfilByType(String type) {
        return Arrays.stream(FindProfilByType.values()).filter(e -> e.getType().equals(type)).findFirst()
                .orElse(TYPE_INCONNU);
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

