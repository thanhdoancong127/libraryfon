package com.ns.borrowing.base;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
public abstract class BaseGenericSelfTest {

    // Book config
    protected static final int BOOK_PORT = 8200;
    protected static final String BOOK_PATH = "/book-service";
    protected static final String BOOK_IMAGE = "book.service";
    // Notification config
    protected static final Integer NOTIFICATION_PORT = 8200;
    protected static final String NOTIFICATION_PATH = "/notification-service";
    // DB config
    protected static final String POSTGRES_ALIAS = "postgres";
    // RabbitMQ
    protected static final String RABBITMQ_ALIAS = "rabbitmq";
    static final Network SHARED_NETWORK = Network.newNetwork();

    @Autowired
	protected TestRestTemplate testRestTemplate;

    @Container
    protected static PostgreSQLContainer<?> dbContainer = new PostgreSQLContainer<>("postgres:16.2")
        .withDatabaseName("borrowingdb")
        .withNetwork(SHARED_NETWORK)
        .withUsername("postgres")
        .withPassword("postgres")
        .withNetworkAliases(POSTGRES_ALIAS)
        .withCopyFileToContainer(MountableFile.forClasspathResource("db/init-scheme.sql"), "/docker-entrypoint-initdb.d/");

    @Container
    protected static RabbitMQContainer rabbitContainer = new RabbitMQContainer("rabbitmq:3-management")
        .withNetwork(SHARED_NETWORK)
        .withNetworkAliases(RABBITMQ_ALIAS);
    
    @Container
    protected static GenericContainer<?> bookAppContainer = new GenericContainer<>("book-service:latest")
        .withEnv(new HashMap<>(
            Map.of(
                "spring.datasource.url", "jdbc:postgresql://" + POSTGRES_ALIAS + ":" + dbContainer.POSTGRESQL_PORT + "/bookdb",
                "spring.datasource.username", dbContainer.getUsername(),
                "spring.datasource.password", dbContainer.getPassword(),
                "server.servlet.context-path", BOOK_PATH
            )
        )).withExposedPorts(BOOK_PORT)
        .withNetwork(SHARED_NETWORK)
        .withNetworkAliases(BOOK_IMAGE)
        .withExposedPorts(BOOK_PORT)
        .dependsOn(dbContainer);

    protected GenericContainer<?> notificationAppContainer = new GenericContainer<>("notification-service:latest")
    .withEnv("server.servlet.context-path", NOTIFICATION_PATH)
    // set up env for db
    .withEnv("spring.datasource.url", "jdbc:postgresql://" + POSTGRES_ALIAS + ":" + dbContainer.POSTGRESQL_PORT + "/notificationdb")
    .withEnv("spring.datasource.username", dbContainer.getUsername())
    .withEnv("spring.datasource.password", dbContainer.getPassword())
    // Setup env for rabbitmq
    .withEnv("spring.rabbitmq.host", rabbitContainer.getHost())
    .withEnv("spring.rabbitmq.port", rabbitContainer.getAmqpPort().toString())
    .withEnv("spring.rabbitmq.username", rabbitContainer.getAdminUsername())
    .withEnv("spring.rabbitmq.password", rabbitContainer.getAdminPassword())
    .withExposedPorts(NOTIFICATION_PORT)
    .withNetwork(SHARED_NETWORK)
    .dependsOn(dbContainer, rabbitContainer);

    @DynamicPropertySource
    static void rabbitProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> dbContainer.getJdbcUrl());
        registry.add("spring.datasource.username", () -> dbContainer.getUsername());
        registry.add("spring.datasource.password", () -> dbContainer.getPassword());
        registry.add("spring.rabbitmq.host", () -> rabbitContainer.getHost());
        registry.add("spring.rabbitmq.port", () -> rabbitContainer.getAmqpPort());
        registry.add("spring.rabbitmq.username", () -> rabbitContainer.getAdminUsername());
        registry.add("spring.rabbitmq.password", () -> rabbitContainer.getAdminPassword());
        registry.add("ns.services.bookPath", () -> "http://127.0.0.1:" + bookAppContainer.getMappedPort(BOOK_PORT) + BOOK_PATH);
    }
}
