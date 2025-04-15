package org.example.netwave.service;

import org.example.netwave.dto.PaymentDTO;
import org.example.netwave.entity.Payment;
import org.example.netwave.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface PaymentService {


    @Transactional
    Payment processPayment(PaymentDTO request, User user);

    @Transactional(readOnly = true)
    Payment findByOrderId(String orderId);

    Map<String, String> generatePaymentHash(String orderId, Double amount);
}
