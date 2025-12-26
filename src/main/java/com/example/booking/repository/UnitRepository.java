package com.example.booking.repository;

import com.example.booking.Entity.Unit;
import com.example.booking.Enum.UnitType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    List<Unit> findAllByProperty_Id(Long propertyId);
    List<Unit> findAllByActiveTrue();
    List<Unit> findAllByProperty_CityIgnoreCase(String city);
    List<Unit> findAllByUnitType(UnitType unitType);
}
