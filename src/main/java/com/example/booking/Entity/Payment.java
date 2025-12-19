package com.example.booking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int amount;

    // CARD / CASH / TRANSFER
    private String method;

    // INITIATED / PAID / FAILED / REFUNDED
    private String status;

    // Kaspi / Stripe ...
    private String provider;

    private LocalDateTime paidAt;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
