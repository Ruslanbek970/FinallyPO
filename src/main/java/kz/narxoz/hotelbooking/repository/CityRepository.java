package kz.narxoz.hotelbooking.repository;

import kz.narxoz.hotelbooking.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
