package com.github.rjansem.microservices.training.dashboard.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

/**
 * Classe principale de l'application permettant de visualiser les appels interservices via Zipkin
 *
 * @author jntakpe
 */
@SpringBootApplication
@EnableZipkinStreamServer
public class DashboardZipkinApplication {

    public static void main(String[] args) {
        SpringApplication.run(DashboardZipkinApplication.class, args);
    }
}
