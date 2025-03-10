package org.example.netwave.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "credit_bundle")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreditBundle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bundleId;

    @Column(nullable = false, length = 100)
    private String bundleName;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private int validityDays;

}
