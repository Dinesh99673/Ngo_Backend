package com.project.Ngo.service;

import com.project.Ngo.DTO.DonationDetails;
import com.project.Ngo.Repository.TransactionRepository;
import com.project.Ngo.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    public BigDecimal getTotalDonation(Long recipient_id){
        String url = "jdbc:postgresql://localhost:5432/NgoDb";
        String user = "postgres";
        String password = "123456";

        // SQL Query to get the sum of the 'amount' column
        String sql = "SELECT SUM(amount) FROM transaction where transaction.recipient_id = "+recipient_id;

        // JDBC Connection
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Retrieve the sum
            if (rs.next()) {
                BigDecimal totalAmount = rs.getBigDecimal(1);
                System.out.println("Total Amount: " + (totalAmount != null ? totalAmount : BigDecimal.ZERO));
                return totalAmount;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new BigDecimal("0.00");
    }

    public List<DonationDetails> getDonationDetails(Long recipient_id){
        String url = "jdbc:postgresql://localhost:5432/NgoDb";
        String user = "postgres";
        String password = "123456";

        List<DonationDetails> donationDetailsDTO = new ArrayList<>();
        // SQL Query to get the sum of the 'amount' column
        String sql = "SELECT p.name, t.amount, t.status, t.transaction_time FROM Transaction t JOIN Profile p ON t.user_id = p.user_id WHERE t.recipient_id = "+recipient_id;

        // JDBC Connection
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Retrieve the sum
            while(rs.next()) {
                DonationDetails donationDetailsTemp = new DonationDetails();
                donationDetailsTemp.setName(rs.getString(1));
                donationDetailsTemp.setAmount(rs.getBigDecimal(2));
                donationDetailsTemp.setStatus(rs.getString(3));
                donationDetailsTemp.setTransactionTime(rs.getTimestamp(4));
                donationDetailsDTO.add(donationDetailsTemp);
                System.out.println("\nbThis is the Time stamp \n "+donationDetailsTemp.getTransactionTime());
            }
            return donationDetailsDTO;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
