package com.github.rjansem.microservices.training.transaction.api;

import com.github.rjansem.microservices.training.transaction.service.GetVKBService;
import com.github.rjansem.microservices.training.apisecurity.ServicesUris;
import com.github.rjansem.microservices.training.transaction.service.GetVKBService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rx.Single;

/**
 * @author mbouhamyd
 */
@Validated
@RestController
@RequestMapping(value = ServicesUris.API + ApiConstants.VKB)
public class VKBResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(VKBResource.class);

    private final GetVKBService getVKBService;

    @Autowired
    public VKBResource(GetVKBService getVKBService) {
        this.getVKBService = getVKBService;
    }


    @RequestMapping(method = RequestMethod.GET)
    public Single<String> findVKB(@RequestParam(required = false) String dim) {
        LOGGER.debug("[ANALYTICS] [] [Transaction] [VKB] dim={}", dim);

        // FIXME Code review Rudy : intégrer la dim sous forme d'enum. La validation automatique renverra un HTTP 400.
        // FIXME Code review Rudy : mettre la valeur par défaut dans le requestParam
        if (StringUtils.isEmpty(dim)) {
            dim = "X";
        }
        return getVKBService.getVKB(dim).toSingle();
    }

}
