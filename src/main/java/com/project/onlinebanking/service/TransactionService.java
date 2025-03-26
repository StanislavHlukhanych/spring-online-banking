package com.project.onlinebanking.service;

import com.project.onlinebanking.entity.*;
import com.project.onlinebanking.exception.InvalidAmountException;
import com.project.onlinebanking.exception.ResourceNotFoundException;
import com.project.onlinebanking.repository.AutomaticTellerMachineRepository;
import com.project.onlinebanking.repository.CardRepository;
import com.project.onlinebanking.repository.TransactionRepository;
import com.project.onlinebanking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final AutomaticTellerMachineRepository automaticTellerMachineRepository;

    public Transaction replenishmentOfCardAccount(String atmNumber, String cardNumber, double amount) {
        AutomaticTellerMachine atm = automaticTellerMachineRepository.findByNumber(atmNumber)
                .orElseThrow(() -> new ResourceNotFoundException("ATM not found"));
        if (amount <= 5) {
            throw new InvalidAmountException("Amount must be greater than 5");
        }
        Card card = cardRepository.findByCardNumber(cardNumber);
        if (card == null) {
            throw new ResourceNotFoundException("Card not found");
        }
        Transaction transaction = new Transaction();
        User userReceiver = card.getUser();
        transaction.setAtm(atm);
        transaction.setReceiver(userReceiver);
        transaction.setReceiverCardNumber(cardNumber);
        transaction.setAmount(amount);
        transaction.setDescription("Replenishment of card account");

        double updateBalance = card.getBalance() + amount;
        card.setBalance(updateBalance);

        return transactionRepository.save(transaction);
    }

    public Transaction transferBetweenCards(String senderCardNumber, String receiverCardNumber, double amount, String description) {
        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be greater than 0");
        }

        Card senderCard = cardRepository.findByCardNumber(senderCardNumber);
        if (senderCard == null) {
            throw new ResourceNotFoundException("Sender card not found");
        }

        Card receiverCard = cardRepository.findByCardNumber(receiverCardNumber);
        if (receiverCard == null) {
            throw new ResourceNotFoundException("Receiver card not found");
        }

        if (senderCard.getBalance() < amount) {
            throw new InvalidAmountException("Insufficient funds");
        }

        Transaction transaction = new Transaction();
        User userSender = senderCard.getUser();
        User userReceiver = receiverCard.getUser();
        transaction.setSender(userSender);
        transaction.setSenderCardNumber(senderCardNumber);
        transaction.setReceiver(userReceiver);
        transaction.setReceiverCardNumber(receiverCardNumber);
        transaction.setAmount(amount);
        transaction.setDescription(description);

        double updateSenderBalance = senderCard.getBalance() - amount;
        double updateReceiverBalance = receiverCard.getBalance() + amount;
        senderCard.setBalance(updateSenderBalance);
        receiverCard.setBalance(updateReceiverBalance);

        return transactionRepository.save(transaction);
    }
}
