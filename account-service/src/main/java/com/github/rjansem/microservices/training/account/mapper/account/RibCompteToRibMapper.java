package com.github.rjansem.microservices.training.account.mapper.account;

import com.github.rjansem.microservices.training.account.domain.efs.compte.RibCompte;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Account;
import com.github.rjansem.microservices.training.account.domain.pbi.account.Rib;
import com.github.rjansem.microservices.training.commons.domain.EfsToPibMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.StringJoiner;

/**
 * Mapper transformant un {@link RibCompte} en {@link Rib}.
 *
 * @author rjansem
 * @see RibCompte
 * @see Rib
 */
public class RibCompteToRibMapper implements EfsToPibMapper<RibCompte, Rib> {

    public RibCompteToRibMapper() {
    }

    @Override
    public Rib map(RibCompte input) {

        final Rib rib = new Rib("",
                "",
                input.getIban(),
                input.getNumeroCompte(),
                input.getIban(),
                input.getBic(),
                input.getDomiciliation().getNom(),
                concatAdressWithCountry(input),
                input.getTitulaire());
        rib.setSubtype("");
        rib.setSubtypeId("");
        return rib;
    }

    public Rib mapAccountIntoRib(Account account, Rib rib) {
        rib.setSubtypeId(account.getSubtypeId());
        rib.setSubtype(account.getSubtype());
        rib.setTypeId(account.getTypeId());
        rib.setType(account.getType());
        return rib;
    }

    private String concatAdressWithCountry(RibCompte input) {
        return new StringJoiner(StringUtils.SPACE) //TODO vérifier
                .add(input.getDomiciliation().getAdresse1())
                .add(input.getDomiciliation().getAdresse2())
                .add(input.getDomiciliation().getAdresse3())
                .add(input.getDomiciliation().getPays())
                .toString();
    }

}
