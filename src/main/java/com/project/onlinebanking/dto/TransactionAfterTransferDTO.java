package com.project.onlinebanking.dto;

import lombok.Data;

@Data
public class TransactionAfterTransferDTO {
    private String name;
    private String amount;
    private String date;
    private String description;

    public TransactionAfterTransferDTO(String username, String amount, String date, String description) {
        this.name = username;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }
}
