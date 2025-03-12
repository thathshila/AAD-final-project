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

    private String offerName;

    private Double discountPercentage;

    private LocalDate validFrom;

    private LocalDate validUntil;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private Packages packages;
}
