package com.project.onlinebanking.service;

import com.project.onlinebanking.entity.Card;
import com.project.onlinebanking.entity.User;
import com.project.onlinebanking.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    public List<Card> getCards(User user) {
        return cardRepository.findAllByUser(user);
    }

    public Card createCard(User user, String pin) {
        if (pin.length() != 4) {
            throw new IllegalArgumentException("Pin must be 4 digits");
        }

        Card card = new Card();
        card.setUser(user);
        card.setCardNumber(generateCardNumber());
        Date date = new Date();
        date.setYear(date.getYear() + 10);
        card.setExpiryDate(date);
        card.setCvv(generateCvv());
        card.setPin(pin);

        return cardRepository.save(card);
    }

    public void deleteCard(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));

        if(card.getBalance() != 0) {
            throw new IllegalArgumentException("Card balance must be 0");
        }

        cardRepository.deleteById(id);
    }

    private String generateCvv() {
        Random random = new Random();
        return String.valueOf(100 + random.nextInt(900));
    }

    private String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }
        return cardNumber.toString();
    }
}
