package com.ns.borrowing.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ns.borrowing.entity.BorrowingEntity;
import com.ns.borrowing.repository.BorrowingRepository;
import com.ns.borrowing.viewmodel.BookDetailVm;
import com.ns.borrowing.viewmodel.BorrowingGetVm;
import com.ns.borrowing.viewmodel.BorrowingPostVm;
import com.ns.common.viewmodel.BorrowingMessage;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

// @SpringBootTest
// @TestPropertySource(properties = {
//     "spring.liquibase.enabled=false"
// })
@ExtendWith(MockitoExtension.class)
public class BorrowingSeviceTest {

    @Mock
    private WebClient webClientMock;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;

    @Mock
    private WebClient.ResponseSpec responseSpecMock;

    @Mock
    private BorrowingRepository borrowingRepository;

    @Mock
    private BorrowingProducer borrowingProducer;

    @InjectMocks
    private BorrowingService borrowingService;

    private ObjectMapper mapper = new ObjectMapper();

    private BookDetailVm bookDetailVm = BookDetailVm.builder()
                                            .author("specific")
                                            .category("dac biet")
                                            .description("spectical")
                                            .id(1L)
                                            .language("EN")
                                            .price(2000.0)
                                            .title("Khong null")
                                            .imageUrl("public/images//public/images/jB2fWfjt2njMhZSY.png")
                                            .build();

    @Test
    public void test_retrieve_borrowing_record_by_valid_id() {

        BorrowingEntity borrowingEntity = new BorrowingEntity();
        borrowingEntity.setId(1L);
        borrowingEntity.setBookId(1L);
        borrowingEntity.setUserId(1L);
        borrowingEntity.setBorrowDate(LocalDate.now());
        borrowingEntity.setReturnDate(LocalDate.now().plusDays(7));

        when(borrowingRepository.findById(1L)).thenReturn(Optional.of(borrowingEntity));

        BorrowingGetVm result = borrowingService.getBorrowingById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(1L, result.bookId());
        assertEquals(1L, result.userId());
    }

    @Test
    public void test_retrieve_all_borrowing_records() {

        // Arrange
        List<BorrowingEntity> borrowingEntities = new ArrayList<>();
        borrowingEntities.add(new BorrowingEntity(1L, 1L, LocalDate.now(), LocalDate.now()));
        borrowingEntities.get(0).setId(1L);

        borrowingEntities.add(new BorrowingEntity(2L, 2L, LocalDate.now(), LocalDate.now()));
        borrowingEntities.get(1).setId(2L);
    
        when(borrowingRepository.findAll()).thenReturn(borrowingEntities);
    
        // Act
        List<BorrowingGetVm> result = borrowingService.getAllBorrowings();
    
        // Assert
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).id());
        assertEquals(2L, result.get(1).id());
        // Add more assertions as needed
    }

    // Successfully save a new borrowing record when book exists
    @Test
    public void test_save_new_borrowing_record_when_book_exists() throws IOException {
        MockWebServer server = new MockWebServer();
        server.start();
        server.enqueue(new MockResponse().setHeader("content-type", MediaType.APPLICATION_JSON).setResponseCode(201).setBody(mapper.writeValueAsString(bookDetailVm)));
        String baseUrl = server.url("/").toString();

        // when
        when(borrowingRepository.saveAndFlush(any(BorrowingEntity.class))).thenReturn(new BorrowingEntity(1L, 1L, LocalDate.now(), LocalDate.now()));

        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        // Arrange
        BorrowingPostVm borrowingPostVm = new BorrowingPostVm(1L, 1L);
        
        borrowingService = new BorrowingService(borrowingRepository
                                        , borrowingProducer,
                                        webClient);
        // inject mocks into service
        setField(borrowingService, "borrowingPath", baseUrl);

        // Act
        borrowingService.saveBorrowingRecord(borrowingPostVm);
    
        // Assert
        verify(borrowingRepository, times(1)).saveAndFlush(any(BorrowingEntity.class));
        verify(borrowingProducer, times(1)).sendBorrowingMessage(any(BorrowingMessage.class));

        server.shutdown();
    }

    // Helper method to set private fields using reflection
    private void setField(Object target, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error setting field " + fieldName + " in " + target.getClass(), e);
        }
    }

    @Test
    public void test_save_new_borrowing_record_when_book() throws IOException {
        MockWebServer server = new MockWebServer();
        server.start();
        server.enqueue(new MockResponse().setHeader("content-type", MediaType.APPLICATION_JSON).setResponseCode(201).setBody(mapper.writeValueAsString(bookDetailVm)));
        String baseUrl = server.url("/").toString();

        // when
        when(borrowingRepository.findById(1L)).thenReturn(Optional.of(new BorrowingEntity(1L, 1L, LocalDate.now(), LocalDate.now())));
        when(borrowingRepository.saveAndFlush(any(BorrowingEntity.class))).thenReturn(new BorrowingEntity(1L, 1L, LocalDate.now(), LocalDate.now()));

        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        borrowingService = new BorrowingService(borrowingRepository
                                        , borrowingProducer,
                                        webClient);
        // inject mocks into service
        setField(borrowingService, "borrowingPath", baseUrl);

        // Act
        borrowingService.updateBorrowingRecord(1L);
    
        // Assert
        verify(borrowingRepository, times(1)).saveAndFlush(any(BorrowingEntity.class));
        verify(borrowingProducer, times(1)).sendBorrowingMessage(any(BorrowingMessage.class));

        server.shutdown();
    }
}
