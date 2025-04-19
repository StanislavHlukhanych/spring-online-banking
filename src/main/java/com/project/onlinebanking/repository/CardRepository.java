package com.project.onlinebanking.repository;

import com.project.onlinebanking.entity.Card;
import com.project.onlinebanking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findAllByUser(User user);
    Optional<Card> findByCardNumber(String cardNumber);
}
