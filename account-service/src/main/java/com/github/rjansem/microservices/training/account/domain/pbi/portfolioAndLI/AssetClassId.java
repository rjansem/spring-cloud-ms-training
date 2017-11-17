package com.github.rjansem.microservices.training.account.domain.pbi.portfolioAndLI;

import java.util.Arrays;

/**
 * Énumération des types des positions
 *
 * @author mbouhamyd
 */
public enum AssetClassId {

    POSITION_ACT("ACT", "Actions"),
    POSITION_DIV("DIV", "Divers"),
    POSITION_FON("FON", "Fonds en euros"),
    POSITION_MON("MON", "Monétaires"),
    POSITION_NC("NC", "Non classés"),
    POSITION_OBL("OBL", "Obligations"),
    POSITION_OBL2("OBL", "OBLIGATIONS"),
    POSITION_TRE("TRE", "Trésorerie"),
    POSITION_NOTEXIST("NOTEXISTS", "NOTEXISTS");

    private final String id;

    private final String name;

    AssetClassId(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /*public static AssetClassId findNameById(String id) {
        return Arrays.stream(AssetClassId.values()).filter(e -> e.getId().equals(id)).findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Unsupported id %s.", id)));
    }
    */
    //TODO demander à EFS la liste des classes (pour ne pas renvoyer le enum par defaut:POSITION_NC )
    public static AssetClassId findNameById(String id) {
        return Arrays.stream(AssetClassId.values()).filter(e -> e.getId().equals(id)).findFirst()
                .orElse(AssetClassId.POSITION_NOTEXIST);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

}
