package com.project.Ngo.DTO;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Utils {

    // Method to generate the HMAC SHA256 signature
    public static String getSignature(String payload, String secret) throws NoSuchAlgorithmException, java.security.InvalidKeyException {
        String generatedSignature = "";
        try {
            // Create an instance of Mac with HMACSHA256 algorithm
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            // Generate the hash based on the concatenated order_id and payment_id
            byte[] hash = sha256_HMAC.doFinal(payload.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array to Base64 encoded string
            generatedSignature = Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generatedSignature;
    }
}
