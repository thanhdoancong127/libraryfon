package com.ns.borrowing.repository;

import org.springframework.stereotype.Repository;

import com.ns.borrowing.entity.BorrowingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BorrowingRepository extends JpaRepository<BorrowingEntity, Long>{
}
