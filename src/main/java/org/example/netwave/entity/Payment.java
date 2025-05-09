//package org.example.netwave.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "payment")
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//public class Payment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int paymentId;
//    private Double amount;
//    @Enumerated(EnumType.STRING)
//    private PaymentMethod paymentMethod;
//    private LocalDateTime paymentDate = LocalDateTime.now();
//    public enum PaymentMethod {
//        CREDIT_CARD, DEBIT_CARD, BANK_TRANSFER
//    }
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//}
package org.example.netwave.entity;

import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    private String orderId;
    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private LocalDateTime paymentDate = LocalDateTime.now();

    // Last 4 digits of card for reference
    private String cardLastFour;

    public enum PaymentMethod {
        CREDIT_CARD, DEBIT_CARD, BANK_TRANSFER
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}