package com.project.onlinebanking.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "atms")
public class AutomaticTellerMachine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String number;
}
