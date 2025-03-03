package com.project.onlinebanking.repository;

import com.project.onlinebanking.entity.Card;
import com.project.onlinebanking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findAllByUser(User user);
    Card findByCardNumber(String cardNumber);
}
