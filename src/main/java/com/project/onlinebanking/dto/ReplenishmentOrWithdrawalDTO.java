package com.project.onlinebanking.dto;

import lombok.Data;

@Data
public class ReplenishmentOrWithdrawalDTO {
    private String atmNumber;
    private String cardNumber;
    private double amount;
}
