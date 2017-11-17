package com.github.rjansem.microservices.training.account;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;

/**
 * Classe principale du service de gestion des comptes clients
 *
 * @author rjansem
 * @author jntakpe
 */
@EnableHystrix
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.github.rjansem.microservices.training")
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

}
