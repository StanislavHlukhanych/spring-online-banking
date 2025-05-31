package com.project.onlinebanking.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "card_images")
public class CardImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String imageUrl;
}
