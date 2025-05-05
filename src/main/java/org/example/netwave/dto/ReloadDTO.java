package org.example.netwave.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReloadDTO {
    private String phoneNumber;
    private double amount;
    @Email(message = "Invalid Email Address")
    private String email;
    private int credit_bundle_id = 2;
}
