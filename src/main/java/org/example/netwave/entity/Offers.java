package org.example.netwave.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "offers")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Offers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int offerId;

    @Column(nullable = false, length = 100)
    private String offerName;

    @Column(nullable = false)
    private Double discountPercentage;

    @Column(nullable = false)
    private LocalDate validFrom;

    @Column(nullable = false)
    private LocalDate validUntil;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false)
    private Packages packages;
}
