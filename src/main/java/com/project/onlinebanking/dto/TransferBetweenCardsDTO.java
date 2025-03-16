package com.project.onlinebanking.dto;

import lombok.Data;

@Data
public class TransferBetweenCardsDTO {
    private String senderCardNumber;
    private String receiverCardNumber;
    private double amount;
    private String description;
}
