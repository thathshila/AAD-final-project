package org.example.netwave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierDTO {
    private int supplierId;
    private String name;
    private String contactEmail;
    private String contactPhone;
    private boolean isDeleted = false;

}
