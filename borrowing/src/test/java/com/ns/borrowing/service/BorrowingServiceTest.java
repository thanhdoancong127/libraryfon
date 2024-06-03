package com.ns.borrowing.service;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.ns.borrowing.viewmodel.BorrowingGetVm;

@Testcontainers
@SpringBootTest
public class BorrowingServiceTest {

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Container
    // @ServiceConnection
    public static PostgreSQLContainer<?> dbContainer = new PostgreSQLContainer<>("postgres:16.2")
        .withDatabaseName("borrowingdb")
        .withUsername("postgres")
        .withPassword("postgres");
        // .withCopyFileToContainer(MountableFile.forClasspathResource("db/init-scheme.sql"), "/docker-entrypoint-initdb.d/");

    @DynamicPropertySource
	public static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url",dbContainer::getJdbcUrl);
		registry.add("spring.datasource.username", dbContainer::getUsername);
		registry.add("spring.datasource.password", dbContainer::getPassword);
	}

    @Test
    @Sql({"/db/init-borrowing.sql"})
    public void shouldGetBookById() {

        BorrowingGetVm borrowingGetVm = borrowingService.getBorrowingById(1L);
        assert borrowingGetVm != null;

        assert borrowingGetVm.id() == 1L;
        assert borrowingGetVm.bookId() == 1L;
        assert borrowingGetVm.userId() == 1L;
        assert "2022-01-01".equals(borrowingGetVm.borrowDate().toString());
        assert "2022-01-02".equals(borrowingGetVm.returnDate().toString());
    }

    @Test
    @Sql({"/db/init-borrowing.sql"})
    public void shouldGetAllBorrowings() {
        List<BorrowingGetVm> borrowingGetVms = borrowingService.getAllBorrowings();
        assert borrowingGetVms != null;
        assert borrowingGetVms.size() == 1;
    }
    
    @AfterEach
    public void cleanUpDatabase() {
        jdbcTemplate.execute("DELETE FROM borrowing");
    }
}
