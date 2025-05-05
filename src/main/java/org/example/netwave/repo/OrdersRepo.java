package org.example.netwave.repo;

import org.example.netwave.dto.OrderDetailsDTO;
import org.example.netwave.entity.OrderDetails;
import org.example.netwave.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdersRepo extends JpaRepository<Orders, Integer> {
    @Query("SELECT SUM(o.totalAmount) FROM Orders o")
    double countOrder();

  //  List<OrderDetailsDTO> findAllByIsDeletedFalse();

    @Query("SELECT o FROM Orders o JOIN FETCH o.user JOIN FETCH o.orderDetails od JOIN FETCH od.item")
    List<Orders> findAllOrdersWithDetails();

    @Query("SELECT o FROM OrderDetails o WHERE o.isDeleted = false")
    List<OrderDetails> findByIsDeletedFalse();
}
