package com.example.booking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.booking.Enum.UnitType;

import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "units")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ROOM / APARTMENT / STUDIO
    @Enumerated(EnumType.STRING)
    private UnitType unitType;

    private String nameOrNumber;

    private int capacityAdults;
    private int capacityChildren;
    private int bedsCount;

    private int baseNightPrice;

    private boolean active;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToMany
    @JoinTable(
            name = "unit_amenities",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private Set<Amenity> amenities;

    @OneToMany(mappedBy = "unit")
    private List<RatePlan> ratePlans;

    @OneToMany(mappedBy = "unit")
    private List<Availability> availabilityList;

    @OneToMany(mappedBy = "unit")
    private List<Booking> bookings;
}