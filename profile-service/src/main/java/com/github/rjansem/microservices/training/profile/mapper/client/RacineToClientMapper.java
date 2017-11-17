package com.github.rjansem.microservices.training.profile.mapper.client;


import com.github.rjansem.microservices.training.commons.domain.EfsToPibMapper;
import com.github.rjansem.microservices.training.profile.domain.efs.Racine;
import com.github.rjansem.microservices.training.profile.domain.pib.Client;

/**
 * Mapper transformant une {@link Racine} en {@link Client}
 *
 * @author jntakpe
 */
public class RacineToClientMapper implements EfsToPibMapper<Racine, Client> {

    @Override
    public Client map(Racine input) {
        return new Client (input.getCode ( ), input.getLibelle ( ), !input.getTitulaire ( ), input.getDroitCredits(), input.getPoids());
    }

}
