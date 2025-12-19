package com.example.booking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.booking.Enum.PropertyType;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // HOTEL / APARTMENT
    @Enumerated(EnumType.STRING)
    private PropertyType type;

    private String name;

    @Column(length = 2000)
    private String description;

    private String city;
    private String address;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "property")
    private List<Unit> units;
}