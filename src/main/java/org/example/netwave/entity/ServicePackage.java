package org.example.netwave.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "service_packages")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServicePackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long packageId;
    private String name;
    private String type;
    private Double price;
    private String description;
    private Integer validityPeriod;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Relationships
    @ManyToMany(mappedBy = "servicePackages")
    private List<User> users;

    @OneToMany(mappedBy = "servicePackage", cascade = CascadeType.ALL)
    private List<Promotion> promotions;

}
