package com.github.rjansem.microservices.training.account.mapper.position;

import com.github.rjansem.microservices.training.account.CasDeTestsUtils;
import com.github.rjansem.microservices.training.account.domain.efs.investissement.InvestissementTitre;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.domain.pbi.position.PositionDetail;
import com.github.rjansem.microservices.training.commons.domain.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Mapper transformant des {@link InvestissementTitre} et {@link Account} en {@link PositionDetail}
 *
 * @author aazzerrifi
 */
public class PortefeuillesToPositionDetailTest {

    @Test
    public void map() throws Exception {
        Account portfeuilles = CasDeTestsUtils.getAccountPF_80000341();
        InvestissementTitre titre = new InvestissementTitre();
        titre.setLibelle("ll");
        titre.setCode("10");
        titre.setQuantite("5");
        titre.setPrixRevientMoyen("33");
        titre.setEstimationEuro("12");
        titre.setCoursCodeDevise("EUR");
        titre.setCodeDevise("EUR");
        titre.setPoids("34");
        titre.setDateCotation("12-01-2016");
        PositionDetail positionDetail = new PositionDetail();
        positionDetail.setAccountNumber(portfeuilles.getAccountNumber());
        positionDetail.setSubtype(portfeuilles.getSubtype());
        positionDetail.setSubtypeId(portfeuilles.getSubtypeId());
        positionDetail.setPositionValue(titre.getLibelle());
        positionDetail.setPositionValueCode(titre.getCode());
        if (StringUtils.isNotBlank(titre.getQuantite())) {
            positionDetail.setAmount(new BigDecimal(titre.getQuantite()));
        }
        BigDecimal returnOnInvestment = new BigDecimal(titre.getPrixRevientMoyen());
        positionDetail.setReturnOnInvestment(returnOnInvestment);
        BigDecimal estimation = new BigDecimal(titre.getEstimationEuro());
        positionDetail.setEstimation(estimation);
        if (titre.getCoursCodeDevise() != null) {
            positionDetail.setReturnOnInvestmentCurrency(titre.getCoursCodeDevise());
        }
        positionDetail.setEstimationCurrency(titre.getCoursCodeDevise());
        if (titre.getCodeDevise() != null) {
            positionDetail.setReturnOnInvestmentCurrency(titre.getCodeDevise());
        }
        positionDetail.setEstimationCurrency(titre.getCodeDevise());
        if (titre.getPoids() != null) {
            BigDecimal percentage = new BigDecimal(titre.getPoids());
            positionDetail.setPercentage(percentage);
        }
        positionDetail.setDate(DateUtils.convertEfsDateToPibLocalDateString(titre.getDateCotation()));
        assertThat(new PortefeuillesToPositionDetail().map(portfeuilles, titre)).isEqualToComparingFieldByField(positionDetail);
    }

}