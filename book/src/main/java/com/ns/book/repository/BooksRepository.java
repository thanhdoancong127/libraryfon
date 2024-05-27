package com.ns.book.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ns.book.entity.BooksEntity;

@Repository
public interface BooksRepository extends JpaRepository<BooksEntity, Long> {
    Optional<BooksEntity> findById(Long id);
    List<BooksEntity> findAll();
    Page<BooksEntity> findAll(Pageable pageable);
    List<BooksEntity> findByTitle(String title);
    List<BooksEntity> findByAuthorName(String name);
    List<BooksEntity> findByAuthorId(Long Id);

    /**
     * This method use for find Publisher in List<UUID> id
     */
    // List<BooksEntity> findByUserIdIn(List<UUID> id, Pageable pageable);
}
