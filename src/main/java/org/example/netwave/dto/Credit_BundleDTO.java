package org.example.netwave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Credit_BundleDTO {
    private int credit_bundle_id;
    private String bundleName;
    private Double amount;
    private String admin_name;
    private int admin_id;
    private boolean isDeleted = false;
}
