package com.ns.borrowing.service.each;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.ns.borrowing.service.BorrowingService;
import com.ns.borrowing.viewmodel.BorrowingGetVm;

@SpringBootTest
@Testcontainers
public class ServiceAllTest {
    
    @Autowired
    private BorrowingService borrowingService;

    @Container
    // @ServiceConnection
    public static PostgreSQLContainer<?> dbContainer = new PostgreSQLContainer<>("postgres:16.2")
        .withDatabaseName("borrowingdb")
        .withUsername("postgres")
        .withPassword("postgres");

    @DynamicPropertySource
	public static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url",dbContainer::getJdbcUrl);
		registry.add("spring.datasource.username", dbContainer::getUsername);
		registry.add("spring.datasource.password", dbContainer::getPassword);
	}

    @Test
    @Sql({"/db/init-borrowing.sql"})
    public void shouldGetAllBorrowings() {
        List<BorrowingGetVm> borrowingGetVms = borrowingService.getAllBorrowings();
        assert borrowingGetVms != null;
        assert borrowingGetVms.size() == 1;
    }
}
