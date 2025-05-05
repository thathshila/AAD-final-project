package org.example.netwave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageActivationRequest {
    private String packageId;
    private String packageName;
    private double price;
    private String phoneNumber;
    private LocalDateTime activationDate;
}