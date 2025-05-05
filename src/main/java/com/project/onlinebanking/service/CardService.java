package com.project.onlinebanking.service;

import com.project.onlinebanking.dto.CardDTO;
import com.project.onlinebanking.dto.CardInfoDTO;
import com.project.onlinebanking.entity.Card;
import com.project.onlinebanking.entity.User;
import com.project.onlinebanking.exception.DeleteCardException;
import com.project.onlinebanking.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    public List<CardDTO> getCards(User user) {
        List<Card> cards = cardRepository.findAllByUser(user);
        cards.sort(Comparator.comparing(Card::getUpdated_at).reversed());

        return cards.stream()
                .map(card -> new CardDTO(card.getCardNumber(), card.getBalance()))
                .toList();
    }

    public CardInfoDTO getCardInfoByCardNumber(String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.yyyy");
        String formattedDate = card.getExpiryDate().toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .format(formatter);

        return new CardInfoDTO(formattedDate, card.getCvv());
    }

    public void createCard(User user, String pin) {
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

        cardRepository.save(card);
    }

    public void deleteCard(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new DeleteCardException("Card not found"));

        if(card.getBalance() != 0) {
            throw new DeleteCardException("Card balance must be 0");
        }

        cardRepository.deleteById(id);
    }

    public void deleteCardByNumber(String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new DeleteCardException("Card not found"));

        if(card.getBalance() != 0) {
            throw new DeleteCardException("Card balance must be 0");
        }

        cardRepository.deleteById(card.getId());
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
