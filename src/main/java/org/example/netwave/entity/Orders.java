package org.example.netwave.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private Date orderDate;
    private String status;
    private double totalAmount;
    private String paymentStatus;
    private LocalDateTime date = LocalDateTime.now();
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetails> orderDetails;
}
