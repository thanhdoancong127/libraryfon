package com.ns.borrowing.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ns.borrowing.service.BorrowingService;
import com.ns.borrowing.viewmodel.BorrowingGetVm;
import com.ns.borrowing.viewmodel.BorrowingPostVm;

import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/borrowing")
@RequiredArgsConstructor
public class BorrowingController {

    private final BorrowingService borrowingService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BorrowingGetVm> getAllBorrowings() {
        return borrowingService.getAllBorrowings();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BorrowingGetVm getBorrowingById(@PathVariable Long id) {
        return borrowingService.getBorrowingById(id);
    }

    @PostMapping("/borrow")
    @ResponseStatus(HttpStatus.CREATED)
    public String borrowBook(@RequestBody BorrowingPostVm borrowingPostVm) {
        // Call service to send borrowing message and save borrowing record
        borrowingService.saveBorrowingRecord(borrowingPostVm);

        return "Borrowing request sent successfully!";
    }

    @PutMapping("/return/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void returnBook(@PathVariable Long id) {
        // Call service to send return message and update borrowing record
        borrowingService.updateBorrowingRecord(id);
    }
}
