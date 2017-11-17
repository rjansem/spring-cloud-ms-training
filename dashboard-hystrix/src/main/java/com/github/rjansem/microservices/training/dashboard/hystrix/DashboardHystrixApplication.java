package com.github.rjansem.microservices.training.dashboard.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;

/**
 * Classe principale de l'application permettant de visualiser les circuits Hystrix
 *
 * @author jntakpe
 */
@EnableTurbineStream
@EnableDiscoveryClient
@SpringBootApplication
@EnableHystrixDashboard
public class DashboardHystrixApplication {

    public static void main(String[] args) {
        SpringApplication.run(DashboardHystrixApplication.class, args);
    }
}
