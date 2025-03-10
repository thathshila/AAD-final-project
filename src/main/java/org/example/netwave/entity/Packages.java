package org.example.netwave.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "packages")
public class Packages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int packageId;

    @Column(nullable = false, length = 100)
    private String packageName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PackageType packageType;

    private Float dataLimit;
    private Integer callMinutes;
    private Integer smsLimit;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private int validityDays;

    @OneToMany(mappedBy = "packages", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Offers> offers;

    public enum PackageType {
        PREPAID, POSTPAID
    }

}

