package kz.narxoz.hotelbooking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "amenities")
@Getter
@Setter
public class Amenity extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
