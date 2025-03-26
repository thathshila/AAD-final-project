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
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private int uid;
       @Column(unique = true)
       private String email;
       private String password;
       private String name;
       private LocalDateTime date = LocalDateTime.now();
       private String role;
       private String status;
       private int phoneNumber;

       @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
       private List<SIM> sims;

       @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
       private List<Payment> payments;

       @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
       private List<UserConnection> connections;

       @Column(name = "deleted", columnDefinition = "boolean default false")
       private boolean deleted;

       @PrePersist
       public void setDefaults() {
            if (this.role == null) {
             this.role = "User";
            }
            if (this.status == null) {
             this.status = "Active";
            }
       }
}
      //    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
      //    private List<Reload> reloads;
