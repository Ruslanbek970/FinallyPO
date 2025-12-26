package kz.narxoz.hotelbooking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "hotels")
@Getter
@Setter
public class Hotel extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "rating", nullable = false)
    private double rating = 0.0;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private User manager;
}
