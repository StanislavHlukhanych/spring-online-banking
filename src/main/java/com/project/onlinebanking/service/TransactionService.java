package com.project.onlinebanking.service;

import com.project.onlinebanking.entity.*;
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
        Transaction transaction = new Transaction();
        AutomaticTellerMachine atm = automaticTellerMachineRepository.findByNumber(atmNumber)
                .orElseThrow(() -> new RuntimeException("ATM not found"));
        Card card = cardRepository.findByCardNumber(cardNumber);
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
        Transaction transaction = new Transaction();
        Card senderCard = cardRepository.findByCardNumber(senderCardNumber);
        Card receiverCard = cardRepository.findByCardNumber(receiverCardNumber);
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
