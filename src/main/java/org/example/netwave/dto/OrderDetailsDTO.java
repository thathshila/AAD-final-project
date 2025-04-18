package org.example.netwave.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
