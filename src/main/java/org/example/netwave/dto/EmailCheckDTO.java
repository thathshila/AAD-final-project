package org.example.netwave.dto;


import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailCheckDTO {
    @Email(message = "Invalid Email Address")
    private String email;

}