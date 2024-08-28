package com.project.Ngo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transaction_id;
    private Long donor_id;
    private Long recipient_id;
    private BigDecimal amount;
    private LocalDateTime transaction_date;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
