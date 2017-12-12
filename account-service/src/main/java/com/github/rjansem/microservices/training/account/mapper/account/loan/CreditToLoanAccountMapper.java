package com.github.rjansem.microservices.training.account.mapper.account.loan;

import com.github.rjansem.microservices.training.account.domain.efs.credit.Credit;
import com.github.rjansem.microservices.training.account.domain.pbi.account.LoanAccount;
import com.github.rjansem.microservices.training.commons.domain.EfsToPibMapper;
import com.github.rjansem.microservices.training.commons.domain.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

import static com.github.rjansem.microservices.training.account.domain.efs.compte.CompteType.CREDIT;

/**
 * Mapper transformant un {@link Credit} en {@link LoanAccount}
 *
 * @author rjansem
 * @see CreditToLoanAccountMapper
 */
public class CreditToLoanAccountMapper implements EfsToPibMapper<Credit, LoanAccount> {

    @Override
    public LoanAccount map(Credit input) {
        Objects.requireNonNull(input);
        LoanAccount account = new LoanAccount();
        account.setId(input.getNumero());
        account.setAccountNumber(input.getNumero());
        account.setType(CREDIT.getLibelle());
        account.setTypeId(CREDIT.getId());
        account.setSubtypeId(input.getNature());
        account.setSubtype(input.getNature());
        account.setOpeningDate(DateUtils.convertEfsDateToLocalDate(input.getDateDebut()));
        account.setExpirationDate(DateUtils.convertEfsDateToLocalDate(input.getMaturite()));
        account.setBookedBalanceCurrency(input.getCodeDevise());
        account.setCurrency(input.getCodeDevise());
        String capitalRestantDu = input.getCapitalRestantDu();
        if (StringUtils.isNotBlank(capitalRestantDu)) {
            account.setBookedBalance(new BigDecimal(capitalRestantDu));
        }
        String montantProchaineEcheance = input.getMontantProchaineEcheance();
        if (StringUtils.isNotBlank(montantProchaineEcheance)) {
            account.setNextAmount(new BigDecimal(montantProchaineEcheance));
        }
        String montantEmprunte = input.getMontantEmprunte();
        if (StringUtils.isNotBlank(montantEmprunte)) {
            account.setInitialBalance(new BigDecimal(montantEmprunte));
            account.setInitialBalanceCurrency(input.getCodeDevise());
        }
        String taux = input.getTaux();
        if (taux != null) {
            account.setInterest(taux.contains(" ") ? taux.replaceAll(" ", "")+"%" : taux+"%");
        }
        String emprunteur = input.getEmprunteur();
        if (emprunteur != null) {
            account.setClientLastName(emprunteur);
        }
        String modeAmortissement = input.getModeAmortissement();
        if (modeAmortissement != null) {
            account.setMode(modeAmortissement);
        }
        String frequenceAmortissement = input.getFrequenceAmortissement();
        if (frequenceAmortissement != null) {
            account.setFrequency(frequenceAmortissement);
        }
        account.setNextDate(DateUtils.convertEfsDateToLocalDate(input.getDateProchaineEcheance()));
        String typePrelevement = input.getTypePrelevement();
        if (typePrelevement != null) {
            account.setInterestType(typePrelevement);
        }
        return account;
    }
}
