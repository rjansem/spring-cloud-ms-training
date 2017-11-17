package com.github.rjansem.microservices.training.account.mapper.portfolioAndLI;


import com.github.rjansem.microservices.training.account.domain.efs.investissement.InvestissementTitre;
import com.github.rjansem.microservices.training.account.domain.pbi.portfolioAndLI.Position;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * mapper qui transforme un titre(portefeuille ou assurance) en objet position
 * *
 *
 * @author mbouhamyd
 */
public class InvestissementTitreToPositionMapper {

    public Position map(InvestissementTitre investissementTitre) {
        Position position = new Position();
        if (investissementTitre != null) {
            position.setPositionName(investissementTitre.getLibelle());
            position.setPositionId(investissementTitre.getCode());
            if (StringUtils.isNotBlank(investissementTitre.getEstimationEuro())) {
                position.setPositionBalance(new BigDecimal(investissementTitre.getEstimationEuro()));
                position.setPositionCurrency("EUR");
            }
            if (StringUtils.isNotBlank(investissementTitre.getPoids())) {
                position.setPercentage(new BigDecimal(investissementTitre.getPoids()));
            }
        }
        return position;
    }
}
