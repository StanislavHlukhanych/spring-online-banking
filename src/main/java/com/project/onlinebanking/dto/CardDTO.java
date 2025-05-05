package com.project.onlinebanking.dto;

import lombok.Data;

@Data
public class CardDTO {
    String cardNumber;
    String balance;

    public CardDTO(String cardNumber, Double balance) {
        this.cardNumber = cardNumber;
        this.balance = String.format("%.2f", balance);
    }
}
