package org.example.netwave.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;

    private String name;
    private String description;
    private double price;
    private String category;
    private int stockQuantity;
    private LocalDateTime date = LocalDateTime.now();
    private String image;
    private boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetails> orderDetails;

}
