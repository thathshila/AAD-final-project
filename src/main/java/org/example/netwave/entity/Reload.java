package org.example.netwave.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reload")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reloadId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "sim_id", nullable = false)
//    private SIM sim;

    @ManyToOne
    @JoinColumn(name = "connection_id")
    private UserConnection userConnection;

    private Double amount;

    private LocalDateTime reloadDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "credit_bundle_id")
    private CreditBundle creditBundle;
}
