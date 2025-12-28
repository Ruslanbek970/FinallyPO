package kz.narxoz.hotelbooking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "guests")
@Getter
@Setter
public class Guest{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "document_number", nullable = false)
    private String documentNumber;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;
}
