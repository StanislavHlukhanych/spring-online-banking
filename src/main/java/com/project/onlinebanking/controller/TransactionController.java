package com.project.onlinebanking.controller;

import com.project.onlinebanking.dto.ReplenishmentOfCardAccountDTO;
import com.project.onlinebanking.dto.TransferBetweenCardsDTO;
import com.project.onlinebanking.entity.Transaction;
import com.project.onlinebanking.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/top-up")
    public ResponseEntity<Transaction> topUp
            (@RequestBody ReplenishmentOfCardAccountDTO replenishmentOfCardAccountDTO) {
        Transaction transaction = transactionService.replenishmentOfCardAccount(
                replenishmentOfCardAccountDTO.getAtmNumber(),
                replenishmentOfCardAccountDTO.getCardNumber(),
                replenishmentOfCardAccountDTO.getAmount()
        );
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer
            (@RequestBody TransferBetweenCardsDTO transferBetweenCardsDTO) {
        Transaction transaction = transactionService.transferBetweenCards(
                transferBetweenCardsDTO.getSenderCardNumber(),
                transferBetweenCardsDTO.getReceiverCardNumber(),
                transferBetweenCardsDTO.getAmount(),
                transferBetweenCardsDTO.getDescription()
        );
        return ResponseEntity.ok(transaction);
    }
}
