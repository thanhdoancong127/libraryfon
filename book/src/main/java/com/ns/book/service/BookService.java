package com.ns.book.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ns.book.entity.BooksEntity;
import com.ns.book.viewmodel.BookPostVm;
import com.ns.book.viewmodel.BookPutVm;
import com.ns.book.viewmodel.BooksGetVm;
import com.ns.book.viewmodel.BookGetVm;
import com.ns.book.repository.AuthorityRepository;
import com.ns.book.repository.BooksRepository;
import com.ns.book.repository.CategoryRepository;

import com.ns.common.exception.NotFoundException;
import com.ns.common.enumeration.Language;
import com.ns.common.util.FileUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BooksRepository booksRepository;
    private final AuthorityRepository authorityRepository;
    private final CategoryRepository categoryRepository;

    @Value("${book.image.path}")
    private String bookImagePath;

    @Value("${book.image.public.path}")
    private String bookImagePublicPath;

    public Page<BookGetVm> getBooks(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return booksRepository.findAll(pageable).map(entity -> BookGetVm.fromEntity(entity, bookImagePublicPath));
    }

    public BookGetVm createBook(BookPostVm bookPostVm) {
        BooksEntity bookEntity = new BooksEntity();
        bookEntity.setTitle(bookPostVm.title());
        bookEntity.setPrice(bookPostVm.price());
        bookEntity.setImageUrl(postBookImagePath(bookPostVm.image()));
        bookEntity.setLanguage(Language.fromAbbreviation(bookPostVm.language()));
        bookEntity.setAuthor(authorityRepository.findById(bookPostVm.authorId()).orElseThrow(() -> new NotFoundException("AUTHORITY_NOT_FOUND", bookPostVm.authorId())));
        bookEntity.setCategory(categoryRepository.findById(bookPostVm.categoryId()).orElseThrow(() -> new NotFoundException("CATEGORY_NOT_FOUND", bookPostVm.categoryId())));
        booksRepository.saveAndFlush(bookEntity);

        return BookGetVm.builder()
            .title(bookEntity.getTitle())
            .price(bookEntity.getPrice())
            .author(bookEntity.getAuthor().getName())
            .category(bookEntity.getCategory().getName())
            .language(bookEntity.getLanguage().getAbbreviation())
            .description(bookEntity.getDescription())
            .imageUrl(bookEntity.getImageUrl())
            .build();
    }

    @Transactional
    public void updateBook(Long id, BookPutVm bookPutVm) {
        BooksEntity bookEntity = booksRepository.findById(id).orElseThrow(() -> new NotFoundException("BOOK_NOT_FOUND", id));
        bookEntity.setTitle(bookPutVm.title());
        bookEntity.setPrice(bookPutVm.price());
        bookEntity.setImageUrl(postBookImagePath(bookPutVm.image()));
        bookEntity.setLanguage(Language.fromAbbreviation(bookPutVm.language()));
        bookEntity.setAuthor(authorityRepository.findById(bookPutVm.authorId()).orElseThrow(() -> new NotFoundException("AUTHORITY_NOT_FOUND", bookPutVm.authorId())));
        bookEntity.setCategory(categoryRepository.findById(bookPutVm.categoryId()).orElseThrow(() -> new NotFoundException("CATEGORY_NOT_FOUND", bookPutVm.categoryId())));
        booksRepository.saveAndFlush(bookEntity);
    }

    public BooksGetVm getBooksByAuthorId(Long authorId) {
        return new BooksGetVm(booksRepository.findByAuthorId(authorId).stream().map(BookGetVm::fromEntity).collect(Collectors.toList()));
    }

    public BookGetVm getBookById(Long id) {
        BooksEntity bookEntity = booksRepository.findById(id).orElseThrow(() -> new NotFoundException("BOOK_NOT_FOUND", id));
        return BookGetVm.fromEntity(bookEntity, bookImagePublicPath);
    }

    private String postBookImagePath(MultipartFile image) {
        String fileName = FileUtils.storeImageToResource(bookImagePath, image);
        return bookImagePublicPath + fileName;
    }

}
