package com.project.onlinebanking.service;

import com.project.onlinebanking.dto.TransactionAfterTransferDTO;
import com.project.onlinebanking.dto.TransactionDTO;
import com.project.onlinebanking.dto.TransactionInfoDTO;
import com.project.onlinebanking.entity.*;
import com.project.onlinebanking.exception.InvalidAmountException;
import com.project.onlinebanking.exception.InvalidCardException;
import com.project.onlinebanking.exception.ResourceNotFoundException;
import com.project.onlinebanking.model.TransactionType;
import com.project.onlinebanking.repository.AutomaticTellerMachineRepository;
import com.project.onlinebanking.repository.CardRepository;
import com.project.onlinebanking.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final AutomaticTellerMachineRepository automaticTellerMachineRepository;

    public List<TransactionDTO> getTransactionsByCardNumber(String cardNumber) {
        List<Transaction> transactions = transactionRepository.findAllBySenderCardNumber(cardNumber);
        transactions.addAll(transactionRepository.findAllByReceiverCardNumber(cardNumber));
        transactions.sort(Comparator.comparing(Transaction::getTimestamp).reversed());
        return transactions.stream()
                .map(transaction -> mapToTransactionDTO(transaction, cardNumber))
                .toList();
    }

    public TransactionInfoDTO getTransactionInfoById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        return new TransactionInfoDTO(
                formatDate(transaction),
                transaction.getDescription()
        );
    }

    public Transaction replenishmentOfCardAccount(String atmNumber, String cardNumber, double amount) {
        AutomaticTellerMachine atm = automaticTellerMachineRepository.findByNumber(atmNumber)
                .orElseThrow(() -> new ResourceNotFoundException("ATM not found"));

        if (amount <= 5) {
            throw new InvalidAmountException("Amount must be greater than 5");
        }

        Card card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));

        Transaction transaction = new Transaction();
        User userReceiver = card.getUser();
        transaction.setAtm(atm);
        transaction.setReceiver(userReceiver);
        transaction.setReceiverCardNumber(cardNumber);
        transaction.setAmount(amount);
        transaction.setDescription("Replenishment of card account");
        transaction.setTransactionType(TransactionType.REPLENISHMENT_FROM_ATM);

        double updateBalance = card.getBalance() + amount;
        card.setBalance(updateBalance);

        return transactionRepository.save(transaction);
    }

    public Transaction withdrawalFromAtm(String atmNumber, String cardNumber, double amount) {
        AutomaticTellerMachine atm = automaticTellerMachineRepository.findByNumber(atmNumber)
                .orElseThrow(() -> new ResourceNotFoundException("ATM not found"));

        if (amount <= 5) {
            throw new InvalidAmountException("Amount must be greater than 5");
        }

        Card card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));

        if (card.getBalance() < amount) {
            throw new InvalidAmountException("Insufficient funds");
        }

        Transaction transaction = new Transaction();
        User userReceiver = card.getUser();
        transaction.setAtm(atm);
        transaction.setReceiver(userReceiver);
        transaction.setReceiverCardNumber(cardNumber);
        transaction.setAmount(amount);
        transaction.setDescription("Withdrawal from ATM");
        transaction.setTransactionType(TransactionType.WITHDRAWAL_FROM_ATM);

        double updateBalance = card.getBalance() - amount;
        card.setBalance(updateBalance);

        return transactionRepository.save(transaction);
    }

    public TransactionAfterTransferDTO transferBetweenCards(String senderCardNumber, String receiverCardNumber, double amount, String description) {
        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be greater than 0");
        }

        Card senderCard = cardRepository.findByCardNumber(senderCardNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Sender card not found"));

        Card receiverCard = cardRepository.findByCardNumber(receiverCardNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Receiver card not found"));

        if (senderCard.getBalance() < amount) {
            throw new InvalidAmountException("Insufficient funds");
        }

        if (senderCard.getId().equals(receiverCard.getId())) {
            throw new InvalidCardException("Cannot transfer to the same card");
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
        transaction.setTransactionType(TransactionType.SEND);

        double updateSenderBalance = senderCard.getBalance() - amount;
        double updateReceiverBalance = receiverCard.getBalance() + amount;
        senderCard.setBalance(updateSenderBalance);
        receiverCard.setBalance(updateReceiverBalance);

        transactionRepository.save(transaction);

        return new TransactionAfterTransferDTO(
                userReceiver.getUsername(),
                "-" + amount,
                formatDate(transaction),
                description
        );
    }

    private static String formatDate(Transaction transaction) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return transaction.getTimestamp()
                .atZone(java.time.ZoneId.systemDefault())
                .format(formatter);
    }

    private TransactionDTO mapToTransactionDTO(Transaction transaction, String cardNumber) {
        return switch (transaction.getTransactionType()) {
            case REPLENISHMENT_FROM_ATM -> new TransactionDTO(
                    transaction.getId(),
                    "ATM " + transaction.getAtm().getNumber(),
                    "+" + transaction.getAmount().toString()
            );
            case WITHDRAWAL_FROM_ATM -> new TransactionDTO(
                    transaction.getId(),
                    "ATM " + transaction.getAtm().getNumber(),
                    "-" + transaction.getAmount().toString()
            );
            default -> {
                boolean isReceiver = transaction.getReceiverCardNumber().equals(cardNumber);
                String username = isReceiver
                        ? transaction.getSender().getUsername()
                        : transaction.getReceiver().getUsername();
                String sign = isReceiver ? "+" : "-";
                yield new TransactionDTO(
                        transaction.getId(),
                        username,
                        sign + transaction.getAmount().toString()
                );
            }
        };
    }
}
