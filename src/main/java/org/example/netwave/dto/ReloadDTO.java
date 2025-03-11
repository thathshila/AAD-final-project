package org.example.netwave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.netwave.entity.CreditBundle;
import org.example.netwave.entity.User;
import org.example.netwave.entity.UserConnection;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReloadDTO {
    private int reloadId;
    private User user;
    private Double amount;
    private UserConnection userConnection;
    private CreditBundle creditBundle;
}
