package org.example.netwave.repo;

import org.example.netwave.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Integer> {
    @Query("SELECT o FROM OrderDetails o WHERE o.isDeleted = false")
    List<OrderDetails> findByIsDeletedFalse();
}
