package com.project.onlinebanking.controller;

import com.project.onlinebanking.dto.*;
import com.project.onlinebanking.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/{cardNumber}")
    public ResponseEntity<List<TransactionDTO>> getTransactionHistory(@PathVariable String cardNumber) {
        return ResponseEntity.ok(transactionService.getTransactionsByCardNumber(cardNumber));
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<TransactionInfoDTO> getTransactionInfo(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionInfoById(id));
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<String> withdrawal
            (@RequestBody ReplenishmentOrWithdrawalDTO replenishmentOrWithdrawalDTO) {
        String response = transactionService.withdrawalFromAtm(
                replenishmentOrWithdrawalDTO.getAtmNumber(),
                replenishmentOrWithdrawalDTO.getCardNumber(),
                replenishmentOrWithdrawalDTO.getAmount()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/top-up")
    public ResponseEntity<String> topUp
            (@RequestBody ReplenishmentOrWithdrawalDTO replenishmentOrWithdrawalDTO) {
        String response = transactionService.replenishmentOfCardAccount(
                replenishmentOrWithdrawalDTO.getAtmNumber(),
                replenishmentOrWithdrawalDTO.getCardNumber(),
                replenishmentOrWithdrawalDTO.getAmount()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionAfterTransferDTO> transfer
            (@RequestBody TransferBetweenCardsDTO transferBetweenCardsDTO) {
        TransactionAfterTransferDTO transaction = transactionService.transferBetweenCards(
                transferBetweenCardsDTO.getSenderCardNumber(),
                transferBetweenCardsDTO.getReceiverCardNumber(),
                transferBetweenCardsDTO.getAmount(),
                transferBetweenCardsDTO.getDescription()
        );
        return ResponseEntity.ok(transaction);
    }
}
