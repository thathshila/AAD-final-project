package org.example.netwave.service;

import jakarta.mail.MessagingException;
import org.example.netwave.dto.PayEmailRequestDTO;

public interface EmailService {
    void sendPackageNotification(String toEmail, String packageName, String packageType, Double price,
                                 String dataLimit, Integer callMinutes, Integer smsLimit, int validityDays);

    void sendPaymentConfirmationEmail(PayEmailRequestDTO emailRequest) throws MessagingException;
}

