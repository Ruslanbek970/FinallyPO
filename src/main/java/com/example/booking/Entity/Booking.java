package com.example.booking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.booking.Enum.BookingStatus;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bookingCode;

    private LocalDate checkIn;
    private LocalDate checkOut;

    private int adults;
    private int children;

    // CREATED / CONFIRMED / CANCELLED / COMPLETED
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private int totalPrice;

    @Column(length = 1000)
    private String guestComment;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private User guest;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "rate_plan_id")
    private RatePlan ratePlan;

    @OneToMany(mappedBy = "booking")
    private List<Payment> payments;

    @OneToOne(mappedBy = "booking")
    private Review review;
}