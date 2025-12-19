package com.example.booking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.booking.Enum.UserStatus;

import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    private String password;

    // ACTIVE / BLOCKED
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "owner")
    private List<Property> properties;

    @OneToMany(mappedBy = "guest")
    private List<Booking> bookings;

    @OneToMany(mappedBy = "author")
    private List<Review> reviews;
}