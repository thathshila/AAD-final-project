package org.example.netwave.controller;

import org.example.netwave.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("api/v1")
public class DashboardController {
    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ReloadService reloadService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/dashboard/user-count")
    public int getTotalUserCount() {
        return userService.getTotalUserCount();
    }

    @GetMapping("/dashboard/supplier-count")
    public int getTotalSupplierCount() {
        return supplierService.getTotalSupplierCount();
    }

    @GetMapping("/dashboard/item-count")
    public int getTotalItemCount() {
        return itemService.getTotalItemCount();
    }

    @GetMapping("/dashboard/reload-count")
    public double getTotalReloadCount() {
        return reloadService.getTotalReloadCount();
    }

    @GetMapping("/dashboard/order-count")
    public double getTotalOrderCount() {
        return orderService.getTotalOrderCount();
    }
}
