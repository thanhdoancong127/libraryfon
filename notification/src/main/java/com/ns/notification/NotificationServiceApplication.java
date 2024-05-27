package com.ns.notification;

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
@EnableJpaRepositories(basePackages = "com.ns.notification.repository")
@EnableJpaAuditing
@ComponentScan(basePackages = {"com.ns.notification", "com.ns.common", "com.ns.common.config", "com.ns.common.component"})
public class NotificationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}
