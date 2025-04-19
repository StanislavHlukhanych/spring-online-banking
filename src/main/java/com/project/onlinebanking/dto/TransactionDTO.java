package com.project.onlinebanking.dto;

import lombok.Data;

@Data
public class TransactionDTO {
    private Long id;
    private String name;
    private String amount;

    public TransactionDTO(Long id, String name, String amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }
}
