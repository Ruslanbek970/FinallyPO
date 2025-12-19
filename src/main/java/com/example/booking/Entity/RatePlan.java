package com.example.booking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.booking.Enum.CancellationPolicy;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rate_plans")
public class RatePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int pricePerNight;

    // FREE_CANCELLATION / NO_REFUND / PARTIAL
    @Enumerated(EnumType.STRING)
    private CancellationPolicy cancellationPolicy;

    private boolean breakfastIncluded;

    private boolean active;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @OneToMany(mappedBy = "ratePlan")
    private List<Booking> bookings;
}