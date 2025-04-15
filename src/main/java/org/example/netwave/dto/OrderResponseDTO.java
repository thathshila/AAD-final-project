package org.example.netwave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private int orderId;
    private String customerName;
    private String email;
    private String phone;
    private String shippingAddress;
    private Date orderDate;
    private String status;
    private double totalAmount;
    private String paymentStatus;
    private List<OrderDetailsDTO> orderDetails;
}