package com.github.rjansem.microservices.training.apisecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Permet de lancer une application Spring Boot pour pouvoir tester les beans Spring
 *
 * @author jntakpe
 */
@SpringBootApplication
public class TestMain {

    public static void main(String[] args) {
        SpringApplication.run(TestMain.class, args);
    }
}
