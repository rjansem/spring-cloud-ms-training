package com.github.rjansem.microservices.training.account.mapper.portfolioAndLI;

import com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.domain.pbi.portfolioAndLI.InvestissementDetail;

import java.math.BigDecimal;

/**
 * mapper qui transforme un account en  investissementDetail (portefeuilles ou assurance)
 *
 * @author mbouhamyd
 */
public class AccountToInvestissementDetailMapper {

    public InvestissementDetail map(Account account) {
        InvestissementDetail invt = new InvestissementDetail();
        invt.setAccountNumber(account.getAccountNumber());
        invt.setType(account.getType());
        invt.setTypeId(account.getTypeId());
        invt.setSubtype(account.getSubtype().toUpperCase());
        invt.setSubtypeId(account.getSubtypeId());
        CompteType compteType = CompteType.fromId(account.getTypeId());
        if (CompteType.PORTEFEUILLE_TITRES.equals(compteType)) {
            invt.setIban(account.getIban());
        }
        invt.setCurrency(account.getCurrency());
        if (account.getCompany() != null) {
            invt.setCompany(account.getCompany().toUpperCase());
        }
        BigDecimal bookedBalance = account.getBookedBalance();
        if (bookedBalance != null) {
            invt.setBookedBalance(bookedBalance);
            invt.setBookedBalanceDate(account.getBookedBalanceDate().substring(0, 10));
        }
        return invt;
    }
}
