package org.example.netwave.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.netwave.entity.UserConnection;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConnectionDTO {
    private int userId;
    @Pattern(regexp = "^[0-9]{10}$",message = "Phone number must be exactly 10 digits")
    private int phoneNumber;
    @NotBlank(message = "Name is Required")
    @Pattern(regexp = "^[A-Za-z]+$",message = "Name must contain only letters and spaces")
    @Size(min = 3,max = 100)
    private String UserName;

}

