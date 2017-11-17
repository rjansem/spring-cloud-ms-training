package com.github.rjansem.microservices.training.account.mapper.portfolioAndLI;

import com.github.rjansem.microservices.training.account.domain.pbi.portfolioAndLI.AssetClass;
import com.github.rjansem.microservices.training.account.domain.pbi.portfolioAndLI.AssetClassId;
import com.github.rjansem.microservices.training.account.domain.pbi.portfolioAndLI.Position;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * mapper qui transforme un goup de titres en group position
 *
 * @author mbouhamyd
 */
public class GroupTitresToGroupPositionsMapper {

    private final String ASSET_NOT_EXIST = "NOTEXISTS";

    private final String CLASSE_EMPTY_NAME = "Non classés";

    private final String CLASSE_EMPTY_ID = "NC";

    private final String FOND_EN_EURO_LI_INTERN = "FONDS EN EUROS";
    private final String ACTIONS = "ACTIONS";
    private final String DIVERS = "DIVERS";
    private final String MONETAIRES = "MONETAIRES";
    private final String NON_CLASSES = "NON CLASSES";
    private final String OBLIGATIONS = "OBLIGATIONS";
    private final String TRESORERIE ="TRESORERIE";


    public Set<AssetClass> mapTitreToPosition(Map<String, Set<Position>> assets) {
        SortedSet<AssetClass> assetsClass = new TreeSet<>();
        assets.forEach((k, v) -> mapSingleAsset(assetsClass, k, v));
        return assetsClass;
    }

    private void mapSingleAsset(Set<AssetClass> assetsClass, String codeISIN, Set<Position> positions) {
        AssetClass ass = new AssetClass();
        if (AssetClassId.findNameById(codeISIN).getName() == ASSET_NOT_EXIST) {
            ass.setAssetClass(codeISIN.length() > 0 ? Character.toUpperCase(codeISIN.charAt(0)) + codeISIN.toLowerCase().substring(1):codeISIN);
            ass.setAssetClassId(CLASSE_EMPTY_ID);

            if(codeISIN.equals(FOND_EN_EURO_LI_INTERN)) {
                ass.setAssetClass(AssetClassId.POSITION_FON.getName());
                ass.setAssetClassId(AssetClassId.POSITION_FON.getId());
            }
            if(codeISIN.equals(ACTIONS)) {
                ass.setAssetClass(AssetClassId.POSITION_ACT.getName());
                ass.setAssetClassId(AssetClassId.POSITION_ACT.getId());
            }
            if(codeISIN.equals(DIVERS)) {
                ass.setAssetClass(AssetClassId.POSITION_DIV.getName());
                ass.setAssetClassId(AssetClassId.POSITION_DIV.getId());
            }
            if(codeISIN.equals(MONETAIRES)) {
                ass.setAssetClass(AssetClassId.POSITION_MON.getName());
                ass.setAssetClassId(AssetClassId.POSITION_MON.getId());
            }
            if(codeISIN.equals(NON_CLASSES)) {
                ass.setAssetClass(AssetClassId.POSITION_NC.getName());
                ass.setAssetClassId(AssetClassId.POSITION_NC.getId());
            }
            if(codeISIN.equals(OBLIGATIONS)) {
                ass.setAssetClass(AssetClassId.POSITION_OBL.getName());
                ass.setAssetClassId(AssetClassId.POSITION_OBL.getId());
            }
            if(codeISIN.equals(TRESORERIE)) {
                ass.setAssetClass(AssetClassId.POSITION_TRE.getName());
                ass.setAssetClassId(AssetClassId.POSITION_TRE.getId());
            }

        } else {
            ass.setAssetClass(AssetClassId.findNameById(codeISIN).getName());
            ass.setAssetClassId(codeISIN);
        }
        BigDecimal percentage = positions.stream()
                .map(Position::getPercentage)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal balance = positions.stream()
                .map(position -> (position.getPositionBalance()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        ass.setPercentage(percentage);
        ass.setAssetClassBalance(balance);
        ass.setPositions(positions.stream().sorted(Comparator.comparing(Position::getPositionName)).collect(Collectors.toList()));
        positions.stream().findAny().map(Position::getPositionCurrency).ifPresent(ass::setAssetClassCurrency);
        assetsClass.add(ass);
    }
}

