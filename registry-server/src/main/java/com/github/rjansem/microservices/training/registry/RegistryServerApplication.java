package com.github.rjansem.microservices.training.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Classe principale de l'application de serveur d'annuaire permettant d'enregistrer les adresses des différents services
 *
 * @author rjansem
 * @author jntakpe
 */
@EnableEurekaServer
@SpringBootApplication
public class RegistryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegistryServerApplication.class, args);
    }
}
