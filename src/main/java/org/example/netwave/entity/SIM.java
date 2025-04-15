package org.example.netwave.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sim")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SIM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int simId;
    @Column(unique = true, length = 20)
    private String simNumber;
    @Enumerated(EnumType.STRING)
    private SIMStatus status = SIMStatus.ACTIVE;
    private LocalDateTime assignedAt = LocalDateTime.now();
    public enum SIMStatus {
        ACTIVE, INACTIVE
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

