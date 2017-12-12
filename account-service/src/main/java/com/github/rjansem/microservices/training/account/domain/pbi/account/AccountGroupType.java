package com.github.rjansem.microservices.training.account.domain.pbi.account;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * Énumération des types de {@link AccountGroup}
 *
 * @author rjansem
 */
public enum AccountGroupType {

    CURRENT_SAVING("CHECKINGS_SAVINGS", "Comptes espèces"),
    INVESTMENTS("INVESTMENTS", "Investissements"),
    LOAN("LOANS", "Crédits"),
    BULOC("BULOC", "Engagements par signature");

    private final String id;

    private final String libelle;

    AccountGroupType(String id, String libelle) {
        this.libelle = libelle;
        this.id = id;
    }

    public static AccountGroupType fromId(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException(String.format("Le id de l'énumération %s ne peut pas être vide", AccountGroupType.class));
        }
        return Arrays.stream(AccountGroupType.values())
                .filter(a -> a.getId().equalsIgnoreCase(id))
                .findAny()
                .orElseThrow(() -> {
                    String message = "Impossible de trouver de correspondance sur l'enum %s pour le id %s";
                    return new IllegalArgumentException(String.format(message, AccountGroupType.class, id));
                });
    }

    public String getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }
}
