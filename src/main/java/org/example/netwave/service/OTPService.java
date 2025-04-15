package org.example.netwave.service;

public interface OTPService {
    void sendOtp(String email);
    boolean verifyOtp(String email, String otp, String newPassword);
}
