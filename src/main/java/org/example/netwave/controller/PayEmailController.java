package org.example.netwave.controller;

import org.example.netwave.dto.PayEmailRequestDTO;
import org.example.netwave.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/email")
public class PayEmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-payment-confirmation")
    public ResponseEntity<?> sendPaymentConfirmation(@RequestBody PayEmailRequestDTO emailRequest) {
        try {
            emailService.sendPaymentConfirmationEmail(emailRequest);
            return ResponseEntity.ok().body("Email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send email: " + e.getMessage());
        }
    }
}