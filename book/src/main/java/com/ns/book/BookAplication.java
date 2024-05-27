package com.ns.book;

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
@EnableJpaRepositories(basePackages = "com.ns.book.repository")
@EnableJpaAuditing
@ComponentScan(basePackages = {"com.ns.book", "com.ns.common", "com.ns.common.config", "com.ns.common.component"})
public class BookAplication {
    public static void main(String[] args) {
        SpringApplication.run(BookAplication.class, args);
    }
}
