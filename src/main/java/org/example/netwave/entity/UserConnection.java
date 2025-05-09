package org.example.netwave.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "connections")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int connectionId;
    private int phoneNumber;
    private String UserName;
    @Enumerated(EnumType.STRING)
    private Connectionstatus status = Connectionstatus.ACTIVE;
    private LocalDateTime assignedAt = LocalDateTime.now();
    public enum Connectionstatus {
        ACTIVE, INACTIVE
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private boolean deleted;
}
