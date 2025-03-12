package org.example.netwave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Credit_BundleDTO {
    private String bundleName;
    private Double amount;
    private int admin_id;
}
