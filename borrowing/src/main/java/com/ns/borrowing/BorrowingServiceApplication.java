package com.ns.borrowing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableDiscoveryClient(autoRegister = false)
@EnableWebSecurity
@EnableJpaRepositories(basePackages = "com.ns.borrowing.repository")
@EnableJpaAuditing
@ComponentScan(basePackages = {"com.ns.borrowing", "com.ns.common", "com.ns.common.config", "com.ns.common.component"})
public class BorrowingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BorrowingServiceApplication.class, args);
    }
}
