package org.example.netwave.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

//@CrossOrigin(origins = "*")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Value("${MERCHANT_ID}")
    private String MERCHANT_ID;

    @Value("${MERCHANT_SECRET}")
    private String MERCHANT_SECRET;


    @GetMapping("/generate-hash")
    public ResponseEntity<Map<String, String>> generateHash(@RequestParam String orderId, @RequestParam String amount) {
        String currency = "LKR"; // Fixed currency
        BigDecimal parsedAmount = new BigDecimal(amount);
        String formattedAmount = String.format("%.2f", parsedAmount); // Ensure 2 decimal places

        Map<String, String> response = new HashMap<>();

        try {
            String merchantHash = hashMD5(MERCHANT_SECRET); // Hash the secret first
            String hashString = MERCHANT_ID + orderId + formattedAmount + currency + merchantHash;
            String hash = hashMD5(hashString).toUpperCase(); // Generate hash

            response.put("hash", hash);
            response.put("merchant_id", MERCHANT_ID);
            response.put("return_url", "http://localhost.com/payment/success");
            response.put("cancel_url", "http://localhost.com/payment/cancel");
            response.put("notify_url", "http://localhost.com/payment/notify");
            response.put("order_id", orderId);
            response.put("amount", formattedAmount);
            response.put("currency", currency);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Error generating hash: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    private String hashMD5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();

        for (byte b : digest) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }


    @PostMapping(value = "/notify", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<String> handlePaymentNotification(@RequestParam Map<String, String> payload) {

        System.out.println("Received notification with payload: " + payload);

        System.out.println("Received PayHere Notification: " + payload);

        String merchantId = payload.get("merchant_id");
        String orderId = payload.get("order_id");
        String paymentId = payload.get("payment_id");
        String payhereAmount = payload.get("payhere_amount");
        String payhereCurrency = payload.get("payhere_currency");
        String statusCode = payload.get("status_code");
        String receivedMd5sig = payload.get("md5sig");

        if (!MERCHANT_ID.equals(merchantId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid merchant");
        }

        String generatedMd5sig = generateMd5Signature(merchantId, orderId, paymentId, payhereAmount, payhereCurrency, statusCode, MERCHANT_SECRET);

        if (!receivedMd5sig.equals(generatedMd5sig)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid signature");
        }
        if ("2".equals(statusCode)) {
            System.out.println("Payment Success for Order ID: " + orderId);
        } else if ("0".equals(statusCode)) {
            System.out.println("Payment Pending for Order ID: " + orderId);
        } else {
            System.out.println("Payment Failed for Order ID: " + orderId + " with status: " + statusCode);
        }
        return ResponseEntity.ok("Notification received");
    }

    @GetMapping("/success")
    public ResponseEntity<String> paymentSuccess(@RequestParam Map<String, String> params) {
        String orderId = params.get("order_id");
        System.out.println("Payment success return for Order ID: " + orderId);
        return ResponseEntity.ok("Payment successful for order: " + orderId);
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> paymentCancelled(@RequestParam Map<String, String> params) {
        String orderId = params.get("order_id");
        System.out.println("Payment cancelled for Order ID: " + orderId);
        return ResponseEntity.ok("Payment cancelled for order: " + orderId);
    }

    private String generateMd5Signature(String merchantId, String orderId, String paymentId, String amount, String currency, String statusCode, String secret) {
        try {
            String md5Secret = hashMD5(secret);
            String data = merchantId + orderId + paymentId + amount + currency + statusCode + md5Secret;
            System.out.println("Received MD5 signature: " + md5Secret);
            return hashMD5(data).toUpperCase();
        } catch (Exception e) {
            throw new RuntimeException("Error generating MD5 signature", e);
        }

    }


}