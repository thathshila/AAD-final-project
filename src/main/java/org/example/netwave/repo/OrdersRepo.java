package org.example.netwave.repo;

import org.example.netwave.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrdersRepo extends JpaRepository<Orders, Integer> {
    @Query("SELECT SUM(o.totalAmount) FROM Orders o")
    double countOrder();
}
