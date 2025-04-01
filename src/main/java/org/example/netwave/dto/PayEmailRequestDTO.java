package org.example.netwave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PayEmailRequestDTO {
    private String to;
    private String subject;
    private String phoneNumber;
    private String amount;
    private String transactionId;
    private String timestamp;
}
