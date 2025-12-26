package com.example.booking.repository;

import com.example.booking.Entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByUnitIdAndDateBetween(Long unitId, LocalDate from, LocalDate to);
}
