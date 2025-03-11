package org.example.netwave.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "admin")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Admin {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminId;

    private String adminName;

    @Column(unique = true, length = 100)
    private String adminEmail;

    private String adminPassword;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreditBundle> creditBundles;
}

