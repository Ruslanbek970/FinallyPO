package com.example.booking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.booking.Enum.PaymentMethod;
import com.example.booking.Enum.PaymentStatus;

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
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    // INITIATED / PAID / FAILED / REFUNDED
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    // Kaspi / Stripe ...
    private String provider;

    private LocalDateTime paidAt;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}