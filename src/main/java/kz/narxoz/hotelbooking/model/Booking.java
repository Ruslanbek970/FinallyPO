package kz.narxoz.hotelbooking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "bookings")
@Getter
@Setter
public class Booking{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "check_in", nullable = false)
    private LocalDate checkInDate;

    @Column(name = "check_out", nullable = false)
    private LocalDate checkOutDate;

    @Column(name = "status", nullable = false)
    private String status; // PENDING, CONFIRMED, CANCELLED

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
}
