package com.project.onlinebanking.repository;

import com.project.onlinebanking.entity.Transaction;
import com.project.onlinebanking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllBySender(User sender);
}
