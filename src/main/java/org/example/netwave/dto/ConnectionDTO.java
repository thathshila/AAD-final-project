package org.example.netwave.dto;

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
   // private int connectionId;
    private int userId;
    private int phoneNumber;
    private String UserName;
 //   private UserConnection.Connectionstatus status;
 //   private LocalDateTime assignedAt;
   // private boolean isDeleted = false;

}

