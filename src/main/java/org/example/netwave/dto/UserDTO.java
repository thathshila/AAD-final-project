package org.example.netwave.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private int uid;
    @Email(message = "Invalid Email Address")
    private String email;
    private String password;
    private String name;
    private String role;
    private String status;
    @Pattern(regexp = "^[0-9]{10}$",message = "Phone number must be exactly 10 digits")
    private String phoneNumber;
    private boolean deleted;
}
