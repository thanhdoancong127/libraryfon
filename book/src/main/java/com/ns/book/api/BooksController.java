package com.ns.book.api;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ns.book.viewmodel.BookPostVm;
import com.ns.book.viewmodel.BookPutVm;
import com.ns.book.viewmodel.BooksGetVm;
import com.ns.book.viewmodel.BookGetVm;
import com.ns.book.service.BookService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookGetVm createBook(BookPostVm bookPostVm) {
        return bookService.createBook(bookPostVm);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<BookGetVm> getBooks(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        return bookService.getBooks(page, size);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookGetVm getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putBook(@PathVariable Long id, @RequestBody BookPutVm bookPutVm) {
        bookService.updateBook(id, bookPutVm);
    }

    @GetMapping("search/author/{authorId}")
    @ResponseStatus(HttpStatus.OK)
    public BooksGetVm getAuthorityByBio(@PathVariable Long authorId) {
        return bookService.getBooksByAuthorId(authorId);
    }
}