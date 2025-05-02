package com.project.onlinebanking.dto;

import lombok.Data;

@Data
public class TransactionInfoDTO {
    private String senderCardNumber;
    private String receiverCardNumber;
    private String date;
    private String description;

    public TransactionInfoDTO(String senderCardNumber, String receiverCardNumber, String date, String description) {
        this.senderCardNumber = senderCardNumber;
        this.receiverCardNumber = receiverCardNumber;
        this.date = date;
        this.description = description;
    }
}
