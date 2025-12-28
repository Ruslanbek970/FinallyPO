package kz.narxoz.hotelbooking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "rooms")
@Getter
@Setter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_number", nullable = false)
    private String roomNumber;

    @Column(name = "type", nullable = false)
    private String type; // STANDARD, DELUXE, SUITE

    @Column(name = "price_per_night", nullable = false)
    private int pricePerNight;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "available", nullable = false)
    private boolean available = true;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "rooms_amenities",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private List<Amenity> amenities;
}
