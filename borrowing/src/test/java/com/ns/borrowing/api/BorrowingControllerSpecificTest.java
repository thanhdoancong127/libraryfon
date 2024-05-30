package com.ns.borrowing.api;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;

import com.ns.borrowing.base.BaseGenericSelfTest;
import com.ns.borrowing.viewmodel.BorrowingPostVm;

public class BorrowingControllerSpecificTest extends BaseGenericSelfTest {

    @BeforeAll
    public static void settup() {
        if(!bookAppContainer.isRunning()) {
            bookAppContainer.start();
        }
    }

    @BeforeEach
    public void setUp() {
        if(!notificationAppContainer.isRunning()) {
            notificationAppContainer.start();
        }
    }

    @Test
    void shouldBorrowBookSuccess() {
        var containerDelegate = new JdbcDatabaseDelegate(dbContainer.withDatabaseName("bookdb"), "");
        ScriptUtils.runInitScript(containerDelegate, "db/init-book.sql");

        BorrowingPostVm postVm = BorrowingPostVm.builder().bookId(1l).userId(1l).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BorrowingPostVm> request = new HttpEntity<>(postVm, headers);

        ResponseEntity<String> borrowingGetVms = testRestTemplate.postForEntity("/borrowing/borrow", request, String.class);
        assert "Borrowing request sent successfully!".equals(borrowingGetVms.getBody());

        ResponseEntity<Object> notification = testRestTemplate.getForEntity("http://127.0.0.1:" + notificationAppContainer.getMappedPort(NOTIFICATION_PORT) +"/notification-service/notification", Object.class);
        System.out.println(notification.getBody());
        assert notification.hasBody() && notification.getStatusCode() == HttpStatus.OK;

        Map mapConverter = (Map) notification.getBody();
        assert mapConverter.get("NotificationGetVm") != null;
    }

    @AfterEach
    public void tearDown() {
        bookAppContainer.stop();
        notificationAppContainer.stop();
    }
}
