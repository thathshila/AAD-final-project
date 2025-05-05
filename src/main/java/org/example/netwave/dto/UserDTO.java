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
public class UserDTO {
    private int uid;
    @Email(message = "Invalid Email Address")
    private String email;
    private String password;
    @NotBlank(message = "Name is Required")
    @Pattern(regexp = "^[A-Za-z]+$",message = "Name must contain only letters and spaces")
    @Size(min = 3,max = 100)
    private String name;
    private String role;
    private String status;
    @Pattern(regexp = "^[0-9]{10}$",message = "Phone number must be exactly 10 digits")
    private String phoneNumber;
    private boolean deleted;

    public UserDTO(int uid, String name) {


    }
}
