package com.project.onlinebanking.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CardInfoDTO {
    String expirationDate;
    String cvv;

    public CardInfoDTO(String expirationDate, String cvv) {
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }
}
