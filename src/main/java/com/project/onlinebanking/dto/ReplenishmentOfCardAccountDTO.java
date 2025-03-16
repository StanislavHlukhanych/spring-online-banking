package com.project.onlinebanking.dto;

import lombok.Data;

@Data
public class ReplenishmentOfCardAccountDTO {
    private String atmNumber;
    private String cardNumber;
    private double amount;
}
