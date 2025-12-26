package kz.narxoz.hotelbooking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "booking_id", unique = true, nullable = false)
    private Booking booking;

    @Column(name = "method", nullable = false)
    private String method; // CARD, CASH

    @Column(name = "status", nullable = false)
    private String status; // PAID, FAILED, PENDING

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;
}
