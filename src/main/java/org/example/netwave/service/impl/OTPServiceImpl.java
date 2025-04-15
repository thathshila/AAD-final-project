package org.example.netwave.service.impl;

import jakarta.transaction.Transactional;
import org.example.netwave.entity.OTPVerification;
import org.example.netwave.entity.User;
import org.example.netwave.repo.OTPRepo;
import org.example.netwave.repo.UserRepo;
import org.example.netwave.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OTPServiceImpl implements OTPService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private OTPRepo otpRepo;

    @Autowired
    private UserRepo userRepo;

    public void sendOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);

        OTPVerification otpVerification = new OTPVerification();
        otpVerification.setEmail(email);
        otpVerification.setOtp(otp);
        otpVerification.setExpiryTime(expiryTime);
        otpRepo.save(otpVerification);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp + "\nIt will expire in 5 minutes.");
        mailSender.send(message);
    }

    @Transactional
    public boolean verifyOtp(String email, String otp, String newPassword) {
        Optional<OTPVerification> otpData = otpRepo.findTopByEmailOrderByExpiryTimeDesc(email);
        if (otpData.isPresent() && otpData.get().getOtp().equals(otp) &&
                otpData.get().getExpiryTime().isAfter(LocalDateTime.now())) {

            User user = userRepo.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            userRepo.save(user);
            otpRepo.delete(otpData.get());
            return true;
        }
        return false;
    }


}
