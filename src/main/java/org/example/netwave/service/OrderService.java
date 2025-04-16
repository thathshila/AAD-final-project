package org.example.netwave.service;

import jakarta.transaction.Transactional;
import org.example.netwave.dto.OrderResponseDTO;
import org.example.netwave.dto.PaymentDTO;
import org.example.netwave.entity.Orders;
import org.example.netwave.entity.User;

public interface OrderService {

    @Transactional
    OrderResponseDTO saveOrderAndDetails(PaymentDTO paymentDTO);

    User getUserByEmail(String email, PaymentDTO paymentDTO);

    OrderResponseDTO mapToOrderResponseDTO(Orders order);

    @Transactional
    void updateOrderStatus(String orderId, int statusCode);

    double getTotalOrderCount();

}
