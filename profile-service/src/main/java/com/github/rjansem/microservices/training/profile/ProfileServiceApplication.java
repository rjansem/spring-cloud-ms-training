package com.github.rjansem.microservices.training.profile;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;

/**
 * Classe principale du service de gestion des profils utilisateurs
 *
 * @author jntakpe
 */
@EnableHystrix
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.github.rjansem.microservices.training")
public class ProfileServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfileServiceApplication.class, args);
    }
}
