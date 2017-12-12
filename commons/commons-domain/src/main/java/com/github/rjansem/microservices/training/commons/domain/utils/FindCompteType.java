package com.github.rjansem.microservices.training.commons.domain.utils;

import java.util.Arrays;

/**
 * Énumération des types de comptes
 *
 * @author rjansem
 */
public enum FindCompteType {

    COURANT_CAV("Compte courant", "DAV", "COMPTE A VUE", "CAV"),
    COURANT_CAR("Compte courant", "DAV", "COMPTE A VUE REMUN.", "CAR"),
    COURANT_CNG("Compte courant", "DAV", "COMPTE NANTI GERE", "CNG"),
    COURANT_CVG("Compte courant", "DAV", "COMPTE GERE", "CVG"),
    COURANT_DCC("Compte courant", "DAV", "COMPTE A VUE", "DCC"),
    COURANT_NAN("Compte courant", "DAV", "COMPTE NANTI", "NAN"),
    COURANT_SSA("Compte courant", "DAV", "COMPTE A VUE REMUN.", "SSA"),

    EPARGNE_LIV("Compte epargne", "Epargne", "CSL", "LIV"),
    EPARGNE_CEL("Compte epargne", "Epargne", "CEL", "CEL"),
    EPARGNE_CLN("Compte epargne", "Epargne", "CSL NANTI", "CLN"),
    EPARGNE_CLP("Compte epargne", "Epargne", "CSL", "CLP"),
    EPARGNE_CPN("Compte epargne", "Epargne", "CSL NANTI", "CPN"),
    EPARGNE_CSL("Compte epargne", "Epargne", "CSL", "CSL"),
    EPARGNE_LDD("Compte epargne", "Epargne", "LDD", "LDD"),
    EPARGNE_LPN("Compte epargne", "Epargne", "LIV. PRIVILEGE NANTI", "LPN"),
    EPARGNE_LVP("Compte epargne", "Epargne", "LIV. PRIVILEGE", "LVP"),
    EPARGNE_PAG("Compte epargne", "Epargne", "PEA GERE", "PAG"),
    EPARGNE_PAN("Compte epargne", "Epargne", "PEA NANTI", "PAN"),
    EPARGNE_PEA("Compte epargne", "Epargne", "PEA", "PEA"),
    EPARGNE_PEL("Compte epargne", "Epargne", "PEL", "PEL"),
    EPARGNE_PGP("Compte epargne", "Epargne", "PEA PME NANTI GERE", "PGP"),
    EPARGNE_PNG("Compte epargne", "Epargne", "PEA NANTI GERE", "PNG"),
    EPARGNE_PPE("Compte epargne", "Epargne", "PEA PME", "PPE"),
    EPARGNE_PPG("Compte epargne", "Epargne", "PEA PME GERE", "PPG"),
    EPARGNE_PPN("Compte epargne", "Epargne", "PEA PME NANTI", "PPN"),

    ASSURANCE_VIE_Interne("Assurance vie", "AssuranceVie", "[Contract Name]", "Interne "),
    ASSURANCE_VIE_Externe("Assurance vie", "AssuranceVie", "[Contract Name]", "Externe "),

    PORTEFEUILLE_TITRES_PEA_GERE("Portefeuile Titres", "PortefeuilleTitres", "COMPTE TITRE PEA GERE", "PEA_GERE"),
    PORTEFEUILLE_TITRES_PEA_NON_GERE("Portefeuile Titres", "PortefeuilleTitres", "COMPTE TITRE PEA", "PEA_NON_GERE"),
    PORTEFEUILLE_TITRES_NON_PEA_GERE("Portefeuile Titres", "PortefeuilleTitres", "COMPTE TITRE GERE", "NON_PEA_GERE"),
    PORTEFEUILLE_TITRES_NON_PEA_NON_GERE("Portefeuile Titres", "PortefeuilleTitres", "COMPTE TITRE", "NON_PEA_NON_GERE"),
    COMPTES_INCONNU("Inconnu", "Inconnu", "Inconnu", "Inconnu");

    private  String libelle;

    private  String id;

    private  String type;

    private  String typeId;

    FindCompteType(String type, String typeId, String libelle, String id) {
        this.libelle = libelle;
        this.id = id;
        this.type = type;
        this.typeId = typeId;
    }

    public static FindCompteType findLibelleById(String type) {
        return Arrays.stream(FindCompteType.values()).filter(e -> e.getLibelle().equals(type)).findFirst()
                //.orElseThrow(() -> new IllegalStateException(String.format("Unsupported type %s.", type)));
                .orElse(COMPTES_INCONNU.setType(type)); // à supprimer dans l'avenir
    }

    public String getLibelle() {
        return libelle;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTypeId() {
        return typeId;
    }

    public FindCompteType setLibelle(String libelle) {
        this.libelle=libelle;
        return this;
    }

    public FindCompteType setId(String id) {
        this.id=id;
        return this;
    }

    public FindCompteType setType(String type) {
        this.type=type;
        return this;
    }

    public FindCompteType setTypeId(String typeId) {
        this.typeId=typeId;
        return this;
    }
}
