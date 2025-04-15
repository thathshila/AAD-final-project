package org.example.netwave.controller;

import jakarta.transaction.Transactional;
import org.example.netwave.dto.OrderResponseDTO;
import org.example.netwave.dto.PaymentDTO;
import org.example.netwave.dto.ResponseDTO;
import org.example.netwave.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private  OrderService orderService;

    @PostMapping("/complete")
    @Transactional
    public ResponseEntity<ResponseDTO> completePayment(@RequestBody PaymentDTO paymentDTO) {
        try {
            System.out.println("Received payment request: " + paymentDTO);
            OrderResponseDTO response = orderService.saveOrderAndDetails(paymentDTO);
            System.out.println("Order processed successfully: " + response.getOrderId());

            return new ResponseEntity<>(
                    new ResponseDTO(
                            200,
                            "Payment processed successfully",
                            response
                    ),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            System.err.println("Error processing payment: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(
                    new ResponseDTO(
                            500,
                            "Error processing payment: " + e.getMessage(),
                            null
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PostMapping("/notify")
    public ResponseEntity<String> handlePaymentNotification(@RequestBody PaymentDTO notification) {
        orderService.updateOrderStatus(notification.getOrder_id(), notification.getStatus_code());

        return ResponseEntity.ok("Notification received");
    }

    @GetMapping("/generate-hash")
    public ResponseEntity<Map<String, String>> generateHash(@RequestParam String orderId, @RequestParam String amount) {
        String currency = "LKR";
        BigDecimal parsedAmount = new BigDecimal(amount);
        String formattedAmount = String.format("%.2f", parsedAmount);

        Map<String, String> response = new HashMap<>();

        try {
            String merchantHash = hashMD5(MERCHANT_SECRET);
            String hashString = MERCHANT_ID + orderId + formattedAmount + currency + merchantHash;
            String hash = hashMD5(hashString).toUpperCase();

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

}