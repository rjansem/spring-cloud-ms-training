package com.github.rjansem.microservices.training.account.util;

import com.github.rjansem.microservices.training.exception.APIException;
import com.github.rjansem.microservices.training.exception.TechnicalCode;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Utilitaire pour la gestion des liens HATEOAS
 *
 * @author rjansem
 */
public class LinksUtils {

    /**
     * Remplace le host name des liens
     * FIXME : à faire via la réécriture d'URL dans API Gateway à terme
     *
     * @param originalUri : URI d'origine
     * @param newDomainName : nouveau nom de domaine
     * @return l'URI avec un autre host name
     */
    public static String changeHostname(String originalUri, String newDomainName) {

        URI uri;
        try {
            uri = new URI(originalUri);
        } catch (URISyntaxException e) {
            throw new APIException("URI invalide", e, TechnicalCode.ILLEGAL_STATE);
        }
        String pattern = uri.getScheme() + "://" + uri.getHost();
        if (uri.getPort() != 443) {
            pattern = pattern + ":" + uri.getPort();
        }
        return originalUri.replace(pattern, newDomainName);
    }
}
