package com.ns.borrowing.service;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

import com.ns.borrowing.viewmodel.BorrowingGetVm;

@SpringBootTest
@DirtiesContext
public class BorrowServiceManualTest {

    @Autowired
    private BorrowingService borrowingService;

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

    @BeforeAll
    public static void setUp() {
        if(!dbContainer.isRunning()) {
            dbContainer.start();
        }
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

    /**
     * Error will happen cause of foreign key
     * And data in perivious test not deleted
     */
    @Test
    @Sql({"/db/init-borrowing.sql"})
    public void shouldGetAllBorrowings() {
        List<BorrowingGetVm> borrowingGetVms = borrowingService.getAllBorrowings();
        assert borrowingGetVms != null;
        assert borrowingGetVms.size() == 1;
    }

    @AfterAll
    public static void tearDown() {
        dbContainer.stop();
    }
}
