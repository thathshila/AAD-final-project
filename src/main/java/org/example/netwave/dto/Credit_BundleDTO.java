package org.example.netwave.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Credit_BundleDTO {
    private int bundleId;
    @NotBlank(message = "Name is Required")
    @Pattern(regexp = "^[A-Za-z]+$",message = "Name must contain only letters and spaces")
    @Size(min = 3,max = 100)
    private String bundleName;
    private double amount;
    private boolean isDeleted = false;
}
