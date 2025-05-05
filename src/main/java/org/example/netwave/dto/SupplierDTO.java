package org.example.netwave.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierDTO {
    private int supplierId;
    @NotBlank(message = "Name is Required")
    @Pattern(regexp = "^[A-Za-z]+$",message = "Name must contain only letters and spaces")
    @Size(min = 3,max = 100)
    private String name;
    @Email(message = "Invalid Email Address")
    private String contactEmail;
    @Pattern(regexp = "^[0-9]{10}$",message = "Phone number must be exactly 10 digits")
    private String contactPhone;
    private boolean isDeleted = false;

}
