package com.project.onlinebanking.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "transactions")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "atm_id")
    private AutomaticTellerMachine atm = null;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_id")
    private User sender = null;

    @Column(name = "sender_card_number")
    private String  senderCardNumber = null;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Column(name = "receiver_card_number")
    private String  receiverCardNumber;

    @Column(nullable = false)
    private Double amount;

    private String description;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime timestamp;
}
