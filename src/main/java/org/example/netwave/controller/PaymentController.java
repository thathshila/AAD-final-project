package org.example.netwave.controller;

import jakarta.transaction.Transactional;
import org.example.netwave.dto.OrderResponseDTO;
import org.example.netwave.dto.PaymentDTO;
import org.example.netwave.dto.ResponseDTO;
import org.example.netwave.entity.Payment;
import org.example.netwave.entity.User;
import org.example.netwave.service.OrderService;
import org.example.netwave.service.PaymentService;
import org.example.netwave.service.UserService;
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

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

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

    @PostMapping("/process")
    public ResponseEntity<ResponseDTO> processPayment(@RequestBody PaymentDTO paymentRequest) {
        try {
            // Find user by email
            User user = userService.findByEmail(paymentRequest.getEmail());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(400, "User not found", null));
            }

            // Process payment
            Payment payment = paymentService.processPayment(paymentRequest, user);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("paymentId", payment.getPaymentId());
            responseData.put("orderId", paymentRequest.getOrderId());
            responseData.put("amount", payment.getAmount());
            responseData.put("paymentDate", payment.getPaymentDate().toString());
            responseData.put("paymentMethod", payment.getPaymentMethod().toString());

            return ResponseEntity.ok(new ResponseDTO(200, "Payment successful", responseData));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "Error processing payment: " + e.getMessage(), null));
        }
    }

    @GetMapping("/details/{orderId}")
    public ResponseEntity<ResponseDTO> getPaymentDetails(@PathVariable String orderId) {
        try {
            Payment payment = paymentService.findByOrderId(orderId);
            if (payment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(404, "Payment not found", null));
            }

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("paymentId", payment.getPaymentId());
            responseData.put("amount", payment.getAmount());
            responseData.put("paymentDate", payment.getPaymentDate().toString());
            responseData.put("paymentMethod", payment.getPaymentMethod().toString());
//            responseData.put("userName", payment.getUser().getFirstName() + " " + payment.getUser().getLastName());

            return ResponseEntity.ok(new ResponseDTO(200, "Payment details retrieved", responseData));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "Error retrieving payment details: " + e.getMessage(), null));
        }
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