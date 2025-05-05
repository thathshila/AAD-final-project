package org.example.netwave.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDTO {
    private int orderDetailId;
    private int itemId;
    private String itemName;
    private int quantity;
    private double itemPrice;
    private double subtotal;
    private int orderId;
    private LocalDate date;
}
