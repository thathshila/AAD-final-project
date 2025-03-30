package org.example.netwave.service;

public interface EmailService {
    void sendPackageNotification(String toEmail, String packageName, String packageType, Double price,
                                 String dataLimit, Integer callMinutes, Integer smsLimit, int validityDays);
}
