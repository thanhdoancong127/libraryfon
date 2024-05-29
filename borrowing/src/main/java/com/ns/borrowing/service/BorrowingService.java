package com.ns.borrowing.service;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import com.ns.borrowing.entity.BorrowingEntity;
import com.ns.borrowing.repository.BorrowingRepository;
import com.ns.borrowing.viewmodel.BookDetailVm;
import com.ns.borrowing.viewmodel.BorrowingGetVm;
import com.ns.borrowing.viewmodel.BorrowingPostVm;
import com.ns.common.exception.NotFoundException;
import com.ns.common.viewmodel.BorrowingMessage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BorrowingService {

    private final BorrowingRepository borrowingRepository;
    private final BorrowingProducer borrowingProducer;
    private final WebClient webClient;

    @Value("${ns.services.bookPath}")
    private String borrowingPath;

    public BorrowingGetVm getBorrowingById(Long id) {
        // Get borrowing record by id
        BorrowingEntity borrowingEntity = borrowingRepository.findById(id).orElseThrow(() -> new NotFoundException("BORROWING_NOT_FOUND", id));
        return new BorrowingGetVm(borrowingEntity.getId(), borrowingEntity.getBookId(), borrowingEntity.getUserId(), borrowingEntity.getBorrowDate(), borrowingEntity.getReturnDate());
    }

    public List < BorrowingGetVm > getAllBorrowings() {
        // Get all borrowing records
        List < BorrowingEntity > borrowingEntities = borrowingRepository.findAll();
        return borrowingEntities.stream().map(borrowingEntity -> new BorrowingGetVm(borrowingEntity.getId(), borrowingEntity.getBookId(), borrowingEntity.getUserId(), borrowingEntity.getBorrowDate(), borrowingEntity.getReturnDate())).toList();
    }

    public void saveBorrowingRecord(BorrowingPostVm borrowingPostVm) {
        try {
            final URI url = UriComponentsBuilder.fromHttpUrl(borrowingPath).path("/books/{id}").buildAndExpand(borrowingPostVm.bookId()).toUri();
            BookDetailVm bookDetailVm = webClient.get().uri(url).retrieve().bodyToMono(BookDetailVm.class).block();
            if (bookDetailVm == null || bookDetailVm.id() == null) {
                throw new NotFoundException("BOOK_NOT_FOUND", borrowingPostVm.bookId());
            }
        } catch (WebClientResponseException e) {
            throw new NotFoundException("BOOK_NOT_FOUND", borrowingPostVm.bookId());
        }

        // Save borrowing record
        BorrowingEntity borrowingEntity = borrowingRepository.saveAndFlush(borrowingPostVm.toBorrowingEntity());

        BorrowingMessage borrowingMessage = BorrowingMessage.builder()
                            .id(borrowingEntity.getId())
                            .bookId(borrowingEntity.getBookId())
                            .userId(borrowingEntity.getUserId())
                            .borrowDate(borrowingEntity.getBorrowDate())
                            .returnDate(borrowingEntity.getReturnDate()).build();

        borrowingProducer.sendBorrowingMessage(borrowingMessage);
    }

    @Transactional(rollbackFor = NotFoundException.class)
    public void updateBorrowingRecord(Long id) {
        final URI url = UriComponentsBuilder.fromHttpUrl(borrowingPath).path("/books/{id}").buildAndExpand(id).toUri();
        BookDetailVm bookDetailVm = webClient.get().uri(url).retrieve().bodyToMono(BookDetailVm.class).block();
        if (bookDetailVm == null) {
            throw new NotFoundException("BOOK_NOT_FOUND", id);
        }

        // Update borrowing record
        BorrowingEntity borrowingEntity = borrowingRepository.findById(id).orElseThrow(() -> new NotFoundException("BORROWING_NOT_FOUND", id));
        LocalDate returnTime = LocalDate.now();
        borrowingEntity.setReturnDate(returnTime);

        borrowingEntity = borrowingRepository.saveAndFlush(borrowingEntity);

        BorrowingMessage borrowingMessage = BorrowingMessage.builder()
                            .id(borrowingEntity.getId())
                            .bookId(borrowingEntity.getBookId())
                            .userId(borrowingEntity.getUserId())
                            .borrowDate(borrowingEntity.getBorrowDate())
                            .returnDate(borrowingEntity.getReturnDate()).build();

        // Send return message
        borrowingProducer.sendBorrowingMessage(borrowingMessage);
    }
}
