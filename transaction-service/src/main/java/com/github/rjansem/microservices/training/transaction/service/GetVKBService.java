package com.github.rjansem.microservices.training.transaction.service;


import com.github.rjansem.microservices.training.transaction.client.GetVkbClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;


/**
 * Services associés à la gestion des credits
 *
 * @author aazzerrifi
 */
@Service
public class GetVKBService {

    private static final Logger LOGGER = LoggerFactory.getLogger (GetVKBService.class);

    private GetVkbClient getVkbClient;

    @Autowired
    public GetVKBService(GetVkbClient getVkbClient) {
        this.getVkbClient = getVkbClient;
    }

    public Observable<String> getVKB(String dim) {

        // FIXME Code review Rudy : très moche. Mettre une enum !!!
        List<String> dims = new ArrayList<>();
        dims.add("X");
        dims.add("S");
        dims.add("M");
        dims.add("L");
        dims.add("XL");
        dims.add("XXL");
        dims.add("XXXL");
        dims.add("x");
        dims.add("s");
        dims.add("m");
        dims.add("l");
        dims.add("xl");
        dims.add("xxl");
        dims.add("xxxl");
        if (dims.contains(dim)) {
            return getVkbClient.getVKB(dim);
        }
        throw new ValidationException(String.format("dim : %s non valide", dim));
    }

}

