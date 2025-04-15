package org.example.netwave.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private int itemId;
    private String name;
    private double price;
    private int quantity;
    private String color;
    private String storage;
}