package kz.narxoz.hotelbooking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "booking_id", unique = true, nullable = false)
    private Booking booking;

    @Column(name = "method", nullable = false)
    private String method;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;
}
