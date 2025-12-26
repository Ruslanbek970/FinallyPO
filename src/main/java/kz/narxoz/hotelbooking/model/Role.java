package kz.narxoz.hotelbooking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role extends BaseEntity {

    @Column(name = "name", unique = true, nullable = false)
    private String name; // ROLE_ADMIN, ROLE_USER, ROLE_MANAGER
}
