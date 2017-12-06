package com.github.rjansem.microservices.training.account.domain.efs.compte;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * Énumération des types de comptes
 *
 * @author rjansem
 * @author aazzerrifi
 */
public enum CompteType {

    COURANT("DAV", "Compte courant"),
    EPARGNE("Epargne", "Compte épargne"),
    DEPOT_A_TERME("DAT", "DEPOT A TERME"),
    PORTEFEUILLE_TITRES("PortefeuilleTitres", "Portefeuille Titres"),
    ASSURANCE_VIE("AssuranceVie", "Assurance vie"),
    CREDIT("Credit", "Crédit"),
    ENGAGEMENT_PAR_SIGNATURE("EngagementSignature", "Engagement par signature");

    private final String id;

    private final String libelle;

    CompteType(String id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public static CompteType fromId(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException(String.format("Le id de l'énumération %s ne peut pas être vide", CompteType.class));
        }
        return Arrays.stream(CompteType.values())
                .filter(c -> c.getId().equalsIgnoreCase(id))
                .findAny()
                .orElseThrow(() -> {
                    String message = String.format("Parametre invalide :%s", id);
                    // String message = "Parametre invalide :  Impossible de trouver de correspondance sur l'enum %s pour le id %s";
                    return new IllegalArgumentException(String.format(message, CompteType.class, id));
                });
    }

    public String getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }
}
