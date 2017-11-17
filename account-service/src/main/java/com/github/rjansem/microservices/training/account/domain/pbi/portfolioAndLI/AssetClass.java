package com.github.rjansem.microservices.training.account.domain.pbi.portfolioAndLI;

import com.github.rjansem.microservices.training.commons.domain.PbiBean;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Objet représentant un group de positions
 *
 * @author mbouhamyd
 */

public class AssetClass implements PbiBean, Comparable<AssetClass> {

    private String assetClass;

    private String assetClassId;

    private BigDecimal percentage;

    private BigDecimal assetClassBalance;

    private String assetClassCurrency;

    private List<Position> positions = new ArrayList<>();

    public String getAssetClass() {
        return assetClass;
    }

    public void setAssetClass(String assetClass) {
        this.assetClass = assetClass;
    }

    public String getAssetClassId() {
        return assetClassId;
    }

    public void setAssetClassId(String assetClassId) {
        this.assetClassId = assetClassId;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public BigDecimal getAssetClassBalance() {
        return assetClassBalance;
    }

    public void setAssetClassBalance(BigDecimal assetClassBalance) {
        this.assetClassBalance = assetClassBalance;
    }

    public String getAssetClassCurrency() {
        return assetClassCurrency;
    }

    public void setAssetClassCurrency(String assetClassCurrency) {
        this.assetClassCurrency = assetClassCurrency;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("assetClass", assetClass)
                .append("assetClassId", assetClassId)
                .append("percentage", percentage)
                .append("assetClassBalance", assetClassBalance)
                .append("assetClassCurrency", assetClassCurrency)
                .append("positions", positions)
                .toString();
    }

    @Override
    public int compareTo(AssetClass other) {
        String FOND_EN_EURO = "Fonds en euros";
        if (this.getAssetClass().equals(FOND_EN_EURO))
            return -1;
        else {
            if (other.getAssetClass().equals(FOND_EN_EURO))
                return 1;
            else
                return getAssetClass().compareTo(other.getAssetClass());
        }

    }
}
