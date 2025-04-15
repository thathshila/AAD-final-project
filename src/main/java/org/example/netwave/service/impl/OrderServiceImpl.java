package org.example.netwave.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.netwave.dto.*;
import org.example.netwave.entity.Item;
import org.example.netwave.entity.OrderDetails;
import org.example.netwave.entity.Orders;
import org.example.netwave.entity.User;
import org.example.netwave.repo.ItemRepo;
import org.example.netwave.repo.OrderDetailsRepo;
import org.example.netwave.repo.OrdersRepo;
import org.example.netwave.repo.UserRepo;
import org.example.netwave.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private  OrdersRepo ordersRepository;
    @Autowired
    private  OrderDetailsRepo orderDetailsRepository;
    @Autowired
    private  ItemRepo itemRepository;
    @Autowired
    private  UserRepo userRepository;

    @Override
    @Transactional
    public OrderResponseDTO saveOrderAndDetails(PaymentDTO paymentDTO) {

        User user = getUserByEmail(paymentDTO.getEmail(), paymentDTO);

        Orders order = new Orders();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus("PENDING");
        order.setTotalAmount(paymentDTO.getAmount());
        order.setPaymentStatus("PENDING");

        Orders savedOrder = ordersRepository.save(order);
        List<OrderDetails> orderDetailsList = new ArrayList<>();

        for (OrderItemDTO itemDTO : paymentDTO.getItems()) {
            Optional<Item> itemOptional = itemRepository.findById(itemDTO.getItemId());

            if (itemOptional.isPresent()) {
                Item item = itemOptional.get();

                OrderDetails orderDetail = new OrderDetails();
                orderDetail.setOrder(savedOrder);
                orderDetail.setItem(item);
                orderDetail.setQuantity(itemDTO.getQuantity());
                orderDetail.setItemPrice(itemDTO.getPrice());
                orderDetail.setSubtotal(itemDTO.getPrice() * itemDTO.getQuantity());

                orderDetailsList.add(orderDetail);

                int newQuantity = item.getStockQuantity() - itemDTO.getQuantity();
                if (newQuantity < 0) {
                    newQuantity = 0; // Prevent negative inventory
                }
                item.setStockQuantity(newQuantity);
                itemRepository.save(item);
            }
        }

        orderDetailsRepository.saveAll(orderDetailsList);
        savedOrder.setOrderDetails(orderDetailsList);

        return mapToOrderResponseDTO(savedOrder);
    }

    @Override
    public User getUserByEmail(String email, PaymentDTO paymentDTO) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {

            User newUser = new User();
            newUser.setName(paymentDTO.getFirst_name() + " " + paymentDTO.getLast_name());
            newUser.setEmail(email);
            newUser.setPhoneNumber(Integer.parseInt(paymentDTO.getPhone()));
            newUser.setRole("CUSTOMER");
            newUser.setStatus("ACTIVE");

            return userRepository.save(newUser);
        }
    }

    @Override
    public OrderResponseDTO mapToOrderResponseDTO(Orders order) {
        OrderResponseDTO responseDTO = new OrderResponseDTO();

        responseDTO.setOrderId(order.getOrderId());
        responseDTO.setCustomerName(order.getUser().getName() + " " + order.getUser().getName());
        responseDTO.setEmail(order.getUser().getEmail());
        responseDTO.setPhone(String.valueOf(order.getUser().getPhoneNumber()));
        responseDTO.setOrderDate(order.getOrderDate());
        responseDTO.setStatus(order.getStatus());
        responseDTO.setTotalAmount(order.getTotalAmount());
        responseDTO.setPaymentStatus(order.getPaymentStatus());

        List<OrderDetailsDTO> orderDetailDTOs = new ArrayList<>();
        for (OrderDetails detail : order.getOrderDetails()) {
            OrderDetailsDTO detailDTO = new OrderDetailsDTO();
            detailDTO.setOrderDetailId(detail.getOrderDetailId());
            detailDTO.setItemId(detail.getItem().getItemId());
            detailDTO.setItemName(detail.getItem().getName());
            detailDTO.setQuantity(detail.getQuantity());
            detailDTO.setItemPrice(detail.getItemPrice());
            detailDTO.setSubtotal(detail.getSubtotal());

            orderDetailDTOs.add(detailDTO);
        }

        responseDTO.setOrderDetails(orderDetailDTOs);
        return responseDTO;
    }

    @Override
    @Transactional
    public void updateOrderStatus(String orderId, int statusCode) {
        String numericPart = orderId.replaceAll("\\D+", "");
        int orderIdNum;

        try {
            orderIdNum = Integer.parseInt(numericPart);
        } catch (NumberFormatException e) {
            return;
        }

        Optional<Orders> orderOptional = ordersRepository.findById(orderIdNum);

        if (orderOptional.isPresent()) {
            Orders order = orderOptional.get();
            switch(statusCode) {
                case 2:
                    order.setStatus("COMPLETED");
                    order.setPaymentStatus("PAID");
                    break;
                case 0:
                    order.setStatus("PENDING");
                    order.setPaymentStatus("PENDING");
                    break;
                case -1:
                    order.setStatus("CANCELED");
                    order.setPaymentStatus("FAILED");
                    break;
                case -2:
                    order.setStatus("EXPIRED");
                    order.setPaymentStatus("EXPIRED");
                    break;
                default:
                    order.setStatus("UNKNOWN");
                    order.setPaymentStatus("UNKNOWN");
            }

            ordersRepository.save(order);
        }
    }
}