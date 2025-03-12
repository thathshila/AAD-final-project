package org.example.netwave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private int uid;
    private String email;
    private String password;
    private String name;
    private String role;
    private String status;
    private String phoneNumber;
   // private boolean isDeleted = false;
}
