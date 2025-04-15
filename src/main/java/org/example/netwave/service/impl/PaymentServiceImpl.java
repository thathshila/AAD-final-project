package org.example.netwave.service.impl;


import lombok.RequiredArgsConstructor;
import org.example.netwave.dto.PaymentDTO;
import org.example.netwave.entity.Payment;
import org.example.netwave.entity.User;
import org.example.netwave.repo.PaymentRepo;
import org.example.netwave.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private  PaymentRepo paymentRepository;

    @Value("${payment.merchant.id:1229893}")
    private String merchantId;

    @Value("${payment.merchant.secret:your_merchant_secret}")
    private String merchantSecret;

    @Value("${payment.return.url:http://localhost:8080/success.html}")
    private String returnUrl;

    @Value("${payment.cancel.url:http://localhost:8080/cart.html}")
    private String cancelUrl;

    @Value("${payment.notify.url:http://localhost:8080/api/v1/payment/notify}")
    private String notifyUrl;

    @Transactional
    @Override
    public Payment processPayment(PaymentDTO request, User user) {
        // Create payment entity
        Payment payment = new Payment();
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(mapCardTypeToPaymentMethod(request.getCardType()));
        payment.setPaymentDate(LocalDateTime.now());
        payment.setUser(user);

        // Save to database
        return paymentRepository.save(payment);
    }

    @Transactional(readOnly = true)
    @Override
    public Payment findByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElse(null);
    }

    @Override
    public Map<String, String> generatePaymentHash(String orderId, Double amount) {
        try {
            // Format: merchantId + orderId + amountInCents + currency + merchantSecret
            String amountString = String.format("%.2f", amount);
            String dataToHash = merchantId + orderId + amountString + "LKR" + merchantSecret;

            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(merchantSecret.getBytes(StandardCharsets.UTF_8),
                    "HmacSHA256");
            sha256Hmac.init(secretKey);

            byte[] hashBytes = sha256Hmac.doFinal(dataToHash.getBytes(StandardCharsets.UTF_8));
            String hash = Base64.getEncoder().encodeToString(hashBytes);

            Map<String, String> response = new HashMap<>();
            response.put("hash", hash);
            response.put("merchant_id", merchantId);
            response.put("return_url", returnUrl);
            response.put("cancel_url", cancelUrl);
            response.put("notify_url", notifyUrl);
            response.put("currency", "LKR");

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate payment hash", e);
        }
    }

    private Payment.PaymentMethod mapCardTypeToPaymentMethod(String cardType) {
        if (cardType == null) {
            return Payment.PaymentMethod.CREDIT_CARD;
        }

        switch (cardType.toLowerCase()) {
            case "visa":
            case "mastercard":
            case "amex":
                return Payment.PaymentMethod.CREDIT_CARD;
            default:
                return Payment.PaymentMethod.CREDIT_CARD;
        }
    }
}
