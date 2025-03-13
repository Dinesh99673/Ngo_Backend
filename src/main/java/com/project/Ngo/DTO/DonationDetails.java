package com.project.Ngo.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class DonationDetails {
    private String name;
    private BigDecimal amount;
    private String status;
    private Timestamp transactionTime;
    // Getters and Setters
}
