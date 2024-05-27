package com.ns.borrowing.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;

import com.ns.borrowing.entity.BorrowingEntity;
import com.ns.borrowing.repository.BorrowingRepository;
import com.ns.borrowing.viewmodel.BookDetailVm;
import com.ns.borrowing.viewmodel.BorrowingPostVm;

import jakarta.activation.DataSource;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ActiveProfiles("test")
public class BorrowingServiceTest {

    @MockBean
    private DataSource dataSource;

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    private BorrowingRepository borrowingRepository;

    @MockBean
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    public void setUp() {

        // Giả lập phản hồi của Book Service
        BookDetailVm bookDetailVm = BookDetailVm.builder()
                .id(1L)
                .title("Sách Hay")
                .price(2000.0)
                .author("Kim Chi")
                .category("Tiểu Thuyết")
                .language("EN")
                .description(null)
                .imageUrl("public/images//public/images/jB2fWfjt2njMhZSY.png")
                .build();

        given(webClient.get()).willReturn(requestHeadersUriSpec);
        given(requestHeadersUriSpec.uri(any(String.class))).willReturn(requestHeadersSpec);
        given(requestHeadersSpec.retrieve()).willReturn(responseSpec);
        given(responseSpec.bodyToMono(BookDetailVm.class)).willReturn(Mono.just(bookDetailVm));

        BorrowingEntity borrowingEntity = new BorrowingEntity();
        borrowingEntity.setBookId(1L);
        borrowingEntity.setUserId(1L);
        borrowingEntity.setBorrowDate(null);
        borrowingEntity.setReturnDate(null);

        given(borrowingRepository.save(any())).willReturn(borrowingEntity);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBorrowBook() {
        // Assuming you have an endpoint to borrow a book
        String url = "/borrowing-service/borrowing/borrow";
        BorrowingPostVm request = new BorrowingPostVm(1L, 1L);

        ResponseEntity<String> string = restTemplate.postForEntity(url, request, String.class);

        System.out.println(string);

        // Verify the WebClient interactions
        // verify(webClient).get();
        // verify(requestHeadersUriSpec).uri(any(String.class));
        // verify(requestHeadersSpec).retrieve();
        // verify(responseSpec).bodyToMono(BookDetailVm.class);
    }

    @Configuration
    static class TestConfig {
        @Bean
        @Primary
        public DataSource dataSource() {
            return org.mockito.Mockito.mock(DataSource.class);
        }
    }
}
