package org.example.netwave.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartItemResponse {
    private String cartItemId;
    private String itemId;
    private String name;
    private String imageUrl;
    private BigDecimal price;
    private Integer quantity;
    private String color;
    private String storage;


}
