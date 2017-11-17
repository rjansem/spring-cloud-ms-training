package com.github.rjansem.microservices.training.account.mapper.position;

import com.github.rjansem.microservices.training.account.domain.efs.investissement.InvestissementTitre;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.domain.pbi.position.PositionDetail;
import com.github.rjansem.microservices.training.commons.domain.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * mapper qui transforme un portefeuilles en positiondetail
 *
 * @author mbouhamyd
 */
public class PortefeuillesToPositionDetail {

    public PositionDetail map(Account portfeuilles, InvestissementTitre titre) {
        PositionDetail positionDetail = new PositionDetail();
        positionDetail.setAccountNumber(portfeuilles.getAccountNumber());
        positionDetail.setSubtype(portfeuilles.getSubtype());
        positionDetail.setSubtypeId(portfeuilles.getSubtypeId());
        positionDetail.setPositionValue(titre.getLibelle());
        positionDetail.setPositionValueCode(titre.getCode());
        if (StringUtils.isNotBlank(titre.getQuantite())) {
            positionDetail.setAmount(new BigDecimal(titre.getQuantite()));
        }
        if (StringUtils.isNotBlank(titre.getPrixRevientMoyen())) {
            BigDecimal returnOnInvestment = new BigDecimal(titre.getPrixRevientMoyen());
            positionDetail.setReturnOnInvestment(returnOnInvestment);
        }
        if (StringUtils.isNotBlank(titre.getEstimationEuro())) {
            BigDecimal estimation = new BigDecimal(titre.getEstimationEuro());
            positionDetail.setEstimation(estimation);
        }
        if (titre.getCoursCodeDevise() != null) {
            positionDetail.setReturnOnInvestmentCurrency(titre.getCoursCodeDevise());
        }
        positionDetail.setEstimationCurrency(titre.getCoursCodeDevise());
        if (titre.getCodeDevise() != null) {
            positionDetail.setReturnOnInvestmentCurrency(titre.getCodeDevise());
        }
        positionDetail.setEstimationCurrency(titre.getCodeDevise());
        if (StringUtils.isNotBlank(titre.getPoids())) {
            BigDecimal percentage = new BigDecimal(titre.getPoids());
            positionDetail.setPercentage(percentage);
        }
        if (StringUtils.isNotBlank(titre.getDateCotation())){
        positionDetail.setDate(DateUtils.convertEfsDateToPibLocalDateString(titre.getDateCotation()));
        }
        return positionDetail;
    }

}
