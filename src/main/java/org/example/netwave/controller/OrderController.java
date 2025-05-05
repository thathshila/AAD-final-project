package org.example.netwave.controller;

import org.example.netwave.service.OrderService;
import org.example.netwave.utill.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("api/v1/orders")
@CrossOrigin("*")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @GetMapping("/getAllOrderDetails")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseUtil getAllOrderDetails() {
        return new ResponseUtil(200, "Get all Order Details",
                orderService.getAllOrderDetails());
    }

}
