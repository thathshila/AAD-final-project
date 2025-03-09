package org.example.netwave.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uid;
    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    private String role;

    private String phoneNumber;
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

//    @Enumerated(EnumType.STRING)
//    private Role role; // Enum: ADMIN, CUSTOMER, EMPLOYEE

//    @Enumerated(EnumType.STRING)
//    private Status status; // Enum: ACTIVE, INACTIVE


    // Relationships
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Payment> payments;

    @ManyToMany
    @JoinTable(
            name = "user_service_package",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "package_id")
    )
    private List<ServicePackage> servicePackages;

}

