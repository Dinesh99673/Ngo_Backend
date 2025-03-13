package com.project.Ngo.controller;

import com.project.Ngo.DTO.DonationDetails;
import com.project.Ngo.model.Ngo;
import com.project.Ngo.model.Profile;
import com.project.Ngo.model.Transaction;
import com.project.Ngo.service.EmailService;
import com.project.Ngo.service.TransactionService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/Transactions")
public class TransactionController implements Serializable {
    private static final long serialVersionUID = 1L;
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public Optional<Transaction> getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @PostMapping(consumes = {"application/json", "application/json;charset=UTF-8"})
    public Transaction saveTransaction(@RequestBody Transaction transaction) {
        return transactionService.saveTransaction(transaction);
    }

    @Value("${razorpay.key_id}")
    private String razorpayKey;

    @Value("${razorpay.key_secret}")
    private String razorpaySecret;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> data) {
        try {


            System.out.println("\nInside the Create order function\n");

            // Initialize Razorpay Client
            RazorpayClient client = new RazorpayClient(razorpayKey, razorpaySecret);
            // Prepare order details
            JSONObject options = new JSONObject();
            options.put("amount", data.get("amount")); // Amount in paise (e.g., 1000 = ₹10)
            options.put("currency", "INR");
            options.put("receipt", "txn_123456");
            // Create the order

            Order order = client.orders.create(options);
            System.out.println("this is");

            System.out.println("\nThiish is the objecct data\n"+order);
            // Return the order details
            return ResponseEntity.ok(order.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/verify-payment")
    public ResponseEntity<String> verifyPayment(@RequestBody Map<String, String> data) {
        String orderId = data.get("razorpay_order_id");
        String paymentId = data.get("razorpay_payment_id");
        String razorpaySignature = data.get("razorpay_signature");
        String secret = razorpaySecret; // Ensure this matches Razorpay's secret

        try {
            // Step 1: Concatenate orderId and paymentId
            String payload = orderId + "|" + paymentId;

            // Debugging: Print payload
            System.out.println("Payload: " + payload);

            // Step 2: Generate HMAC-SHA256 signature
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            String generatedSignature = toHexString(mac.doFinal(payload.getBytes(StandardCharsets.UTF_8)));

            // Debugging: Print signatures
            System.out.println("Generated Signature: " + generatedSignature);
            System.out.println("Razorpay Signature: " + razorpaySignature);

            // Step 3: Compare signatures
            if (generatedSignature.equals(razorpaySignature)) {
                Transaction payment = new Transaction();
                Profile donar = new Profile();
                Ngo recipient = new Ngo();
                recipient.setNgo_id(Long.parseLong(data.get("recipient_id")));
                donar.setUser_id(Long.parseLong("1"));
                payment.setStatus("Success");
                payment.setAmount(new BigDecimal(data.get("amount")));
                payment.setDonor(donar);
                payment.setRecipientNgo(recipient);
                payment.setPaymentId(paymentId);
                payment.setOrderId(orderId);
                payment.setSignature(razorpaySignature);
                transactionService.saveTransaction(payment);
                System.out.println("\ninside the signature module \n");
                handleTransactionSuccess("dc7821836954@gmail.com",new BigDecimal(data.get("amount")),data.get("NgoName"));
                return ResponseEntity.ok("Payment verified successfully");
            } else {
                Transaction payment = new Transaction();
                Profile donar = new Profile();
                Ngo recipient = new Ngo();
                recipient.setNgo_id(Long.parseLong(data.get("recipient_id")));
                donar.setUser_id(Long.parseLong(data.get("1")));
                payment.setStatus("Failed");
                payment.setAmount(new BigDecimal(data.get("amount")));
                payment.setDonor(donar);
                payment.setRecipientNgo(recipient);
                payment.setPaymentId(paymentId);
                payment.setOrderId(orderId);
                payment.setSignature(razorpaySignature);
                transactionService.saveTransaction(payment);
                return ResponseEntity.badRequest().body("Invalid signature");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during signature verification");
        }
    }

    public void handleTransactionSuccess(String donorEmail,BigDecimal amt,String name) {
        String subject = "Donation Receipt";
        String body = "Thank you for your donation to "+name+"! Your transaction of Rs "+amt+" was successful.";
        System.out.println("\nmail sended properly\n");
        emailService.sendReceiptEmail(donorEmail, subject, body);
        System.out.println("\nmail sended properly\n");
    }

    // Helper method to convert byte array to hexadecimal string
    private String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @GetMapping("/total_donation/{id}")
    public BigDecimal getTotalDonation(@PathVariable Long id) {
        return transactionService.getTotalDonation(id);
    }

    @GetMapping("/donationDetails/{id}")
    public List<DonationDetails> getDonationDetails(@PathVariable Long id) {
        return transactionService.getDonationDetails(id);
    }


    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }
}
