package com.ns.borrowing.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
public abstract class BaseGenericMTest {

    @Autowired
	protected TestRestTemplate testRestTemplate ;

    @Container
    // @ServiceConnection
    public static PostgreSQLContainer<?> dbContainer = new PostgreSQLContainer<>("postgres:16.2");

    @DynamicPropertySource
	public static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url",dbContainer::getJdbcUrl);
		registry.add("spring.datasource.username", dbContainer::getUsername);
		registry.add("spring.datasource.password", dbContainer::getPassword);
	}
}
