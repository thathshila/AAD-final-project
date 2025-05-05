package org.example.netwave.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "active_packages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivePackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String packageId;
    private String packageName;
    private double price;
    private String phoneNumber;
    private LocalDateTime activationDate;
    private LocalDateTime expiryDate;
    private Boolean isActive = true;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.activationDate == null) {
            this.activationDate = LocalDateTime.now();
        }
        // Set expiry date 30 days from activation by default
        if (this.expiryDate == null) {
            this.expiryDate = this.activationDate.plusDays(30);
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}