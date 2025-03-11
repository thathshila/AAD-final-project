package org.example.netwave.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    private LocalDateTime date = LocalDateTime.now();

    @OneToMany(mappedBy = "creditBundle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Packages> packages;

    @OneToMany(mappedBy = "creditBundle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reload> reloads;


    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

}
