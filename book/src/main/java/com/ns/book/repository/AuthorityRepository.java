package com.ns.book.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ns.book.entity.AuthorEntity;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorEntity, Long>{

    public List<AuthorEntity> findByNameContainingIgnoreCase(String name);
    public Optional<AuthorEntity> findById(Long id);
    
}
