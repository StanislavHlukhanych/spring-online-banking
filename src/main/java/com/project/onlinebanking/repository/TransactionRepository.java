package com.project.onlinebanking.repository;

import com.project.onlinebanking.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllBySenderCardNumber(String cardNumber);
    List<Transaction> findAllByReceiverCardNumber(String cardNumber);
}
