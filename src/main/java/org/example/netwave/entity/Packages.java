package org.example.netwave.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "packages")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Packages {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int packageId;

    @Column(nullable = false, length = 100)
    private String packageName;

    private String packageType;

    private Float dataLimit;
    private Integer callMinutes;
    private Integer smsLimit;

    private Double price;

    private int validityDays;

    @OneToMany(mappedBy = "packages", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Offers> offers;

//    @ManyToOne
//    @JoinColumn(name = "sim_id", nullable = false)
//    private SIM sim;

    @ManyToOne
    @JoinColumn(name = "credit_bundle_id")
    private CreditBundle creditBundle;

//    @ManyToOne
//    private UserConnection userConnection;

}
