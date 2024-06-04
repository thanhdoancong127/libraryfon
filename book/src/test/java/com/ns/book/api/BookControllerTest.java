package com.ns.book.api;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ns.book.base.BaseSpecificMTest;

/**
 * BookControllerTest
 */
public class BookControllerTest extends BaseSpecificMTest {

    @Test
    @Sql({ "/db/init-book.sql" })
    void shouldCreateBookSuccess() {
        
        // Create form data
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("title", "title");
        formData.add("price", "10.0");
        formData.add("language", "EN");
        formData.add("authorId", "1");
        formData.add("categoryId", "1");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);

        ResponseEntity<Object> response = testRestTemplate.postForEntity("/books", requestEntity, Object.class);
        assert response.getStatusCode() == HttpStatus.CREATED;
    }
}