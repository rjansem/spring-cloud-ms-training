package com.github.rjansem.microservices.training.account.mapper.position;

import com.github.rjansem.microservices.training.account.domain.efs.investissement.InvestissementTitre;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.domain.pbi.position.PositionDetail;
import com.github.rjansem.microservices.training.commons.domain.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * mapper qui transforme une assurane en positiondetail
 *
 * @author mbouhamyd
 */
public class AssuranceToPositionDetail {

    public PositionDetail map(Account assurance, InvestissementTitre titre) {
        PositionDetail positionDetail = new PositionDetail();
        positionDetail.setAccountNumber(assurance.getAccountNumber());
        positionDetail.setSubtypeId(assurance.getSubtypeId());
        positionDetail.setSubtype(assurance.getSubtype());
        positionDetail.setCompany(assurance.getCompany());
        positionDetail.setPositionValue(titre.getLibelle());
        positionDetail.setPositionValueCode(titre.getCode());
        if (StringUtils.isNotBlank(titre.getQuantite())) {
            positionDetail.setAmount(new BigDecimal(titre.getQuantite()));
        }
        if(StringUtils.isNotBlank(titre.getEstimationEuro())) {
            BigDecimal estimation = new BigDecimal(titre.getEstimationEuro());
            positionDetail.setEstimation(estimation);
            positionDetail.setEstimationCurrency("EUR");
        }
        String cours = titre.getCours();
        if (StringUtils.isNotBlank(cours)) {
            BigDecimal rate = new BigDecimal(titre.getCours());
            positionDetail.setRate(rate);
            positionDetail.setRateCurrency(titre.getCoursCodeDevise());
        }
        String revientMoyen = titre.getPrixRevientMoyen();
        if (StringUtils.isNotBlank(revientMoyen)) {
            BigDecimal returnOnInvestment = new BigDecimal(revientMoyen);
            positionDetail.setReturnOnInvestment(returnOnInvestment);
            positionDetail.setReturnOnInvestmentCurrency(titre.getCoursCodeDevise());
        }
        if (StringUtils.isNotBlank(titre.getPoids())) {
            BigDecimal percentage = new BigDecimal(titre.getPoids());
            positionDetail.setPercentage(percentage);
        }
        if (StringUtils.isNotBlank(titre.getDateCotation())) {
            positionDetail.setDate(DateUtils.convertEfsDateToPibLocalDateString(titre.getDateCotation()));
        }
        return positionDetail;
    }

}
