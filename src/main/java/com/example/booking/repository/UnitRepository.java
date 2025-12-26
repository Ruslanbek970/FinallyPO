package com.example.booking.repository;

import com.example.booking.Entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    List<Unit> findByPropertyId(Long propertyId);
}
