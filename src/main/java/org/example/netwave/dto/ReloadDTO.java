package org.example.netwave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReloadDTO {
    private String phoneNumber;
    private double amount;
    private String email;
    private int credit_bundle_id = 2;
}
