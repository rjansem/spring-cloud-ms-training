package com.github.rjansem.microservices.training.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;

/**
 * Classe principale de l'application permettant de lancer le proxy Zuul
 *
 * @author jntakpe
 */
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

    /**
     * Intégration d'un pattern pour la gestion des numéros de version des API dynamique :
     * <pre>XXX-service-vX-Y</pre> aura pour route <pre>XXX-service/vX.Y</pre> de manière dynamique.
     *
     * @return le {@link PatternServiceRouteMapper}
     */
    @Bean
    public PatternServiceRouteMapper serviceRouteMapper() {
        return new PatternServiceRouteMapper(
                "(?<name>^.+)-(?<versionmaj>v[0-9]+)-(?<versionmin>[0-9]+$)",
                "${name}/${versionmaj}.${versionmin}");
    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
