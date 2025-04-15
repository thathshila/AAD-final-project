package org.example.netwave.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String country;

    private String order_id;
    private String merchant_id;
    private String return_url;
    private String cancel_url;
    private String notify_url;
    private String currency;
    private String hash;
    private double amount;

    private int status_code;
    private List<OrderItemDTO> items;
}