package org.example.netwave.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDTO {
    private int itemId;
    private String name;
    private String description;
    private double price;
    private String category;
    private int stockQuantity;
    private String image;
    private int supplierId;
    private boolean isDeleted = false;

}
