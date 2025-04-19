package com.project.onlinebanking.dto;

import lombok.Data;

@Data
public class TransactionInfoDTO {
    private String date;
    private String description;

    public TransactionInfoDTO(String date, String description) {
        this.date = date;
        this.description = description;
    }
}
