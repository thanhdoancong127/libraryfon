package com.ns.borrowing.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.ns.borrowing.viewmodel.BookDetailVm;
import com.ns.borrowing.viewmodel.BorrowingPostVm;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@WireMockTest(httpPort = 8200)
public class BorrowingControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() throws JsonProcessingException {

        WireMock.stubFor(get(urlEqualTo("/book-service/books/1"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(mapper.writeValueAsString(
                            BookDetailVm.builder()
                                .author("specific")
                                .category("dac biet")
                                .description("spectical")
                                .id(1L)
                                .language("EN")
                                .price(2000.0)
                                .title("Khong null")
                                .imageUrl("public/images//public/images/jB2fWfjt2njMhZSY.png")
                                .build()
                            )
                        )
                        .withStatus(200)));
    }

    @Test
    public void testBorrowBook() {
        BorrowingPostVm postVm = BorrowingPostVm.builder().bookId(1l).userId(1l).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BorrowingPostVm> request = new HttpEntity<>(postVm, headers);

        ResponseEntity<String> borrowingGetVms = restTemplate.postForEntity("/borrowing/borrow", request, String.class);
        System.out.println(borrowingGetVms);

    }
}
