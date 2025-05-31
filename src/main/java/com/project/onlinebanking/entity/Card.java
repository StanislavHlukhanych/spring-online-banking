package com.project.onlinebanking.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "cards")
public class Card implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @Column(unique = true, nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private Date expiryDate;

    @Column(nullable = false)
    private String cvv;

    @Column(nullable = false)
    private String pin;

    @Column(nullable = false)
    private Double balance = 0.0;

    @ManyToOne()
    @JoinColumn(name = "card_image_id")
    private CardImage cardImage;

    @Column(nullable = false)
    @CreationTimestamp
    private Date created_at;

    @Column(nullable = false)
    @UpdateTimestamp
    private Date updated_at;
}
