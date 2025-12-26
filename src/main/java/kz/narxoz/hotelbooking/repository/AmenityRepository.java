package kz.narxoz.hotelbooking.repository;

import kz.narxoz.hotelbooking.model.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {
}
