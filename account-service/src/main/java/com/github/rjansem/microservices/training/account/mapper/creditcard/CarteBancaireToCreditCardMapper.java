package com.github.rjansem.microservices.training.account.mapper.creditcard;

import com.github.rjansem.microservices.training.account.domain.efs.cartebancaire.CarteBancaire;
import com.github.rjansem.microservices.training.account.domain.pbi.cartebancaire.CreditCard;
import com.github.rjansem.microservices.training.commons.domain.EfsToPibMapper;
import com.github.rjansem.microservices.training.commons.domain.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * Mapper transformant une {@link CarteBancaire Bancaire} en {@link CreditCard}
 *
 * @author mbouhamyd
 */
public class CarteBancaireToCreditCardMapper implements EfsToPibMapper<CarteBancaire, CreditCard> {

    private final static String CODE_CRYPT = "XXXXXX";

    @Override
    public CreditCard map(CarteBancaire input) {
        CreditCard creditCard = new CreditCard();
        if (input.getNumero() != null) {
            if (!input.getNumero().isEmpty() && input.getNumero().length() > 15) {
                String start = input.getNumero().substring(3, 9);
                String end = input.getNumero().substring(15);
                String numero = start + CODE_CRYPT + end;
                creditCard.setCardNumber(numero);
            }
            creditCard.setCreditcardId(String.valueOf(input.getId()));
            creditCard.setCardType(input.getType());
            creditCard.setCardName(input.getTitulaire());
            if (StringUtils.isNotBlank(input.getEncours())) {
                creditCard.setCardBalance(new BigDecimal(input.getEncours()));
            }
            creditCard.setCardCurrency(String.valueOf(input.getDevise()));
            creditCard.setDate(DateUtils.convertEfsDateToPibLocalDateString(input.getDatePrelevement()));
        }
        return creditCard;

    }

}
