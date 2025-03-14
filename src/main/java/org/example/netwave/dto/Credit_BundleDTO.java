package org.example.netwave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Credit_BundleDTO {
    private int bundleId;
    private String bundleName;
    private double amount;
    private boolean isDeleted = false;
}
