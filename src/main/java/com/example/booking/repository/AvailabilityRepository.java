package com.example.booking.repository;

import com.example.booking.Entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findAllByUnit_IdAndDateBetween(Long unitId, LocalDate start, LocalDate end);
    Optional<Availability> findByUnit_IdAndDate(Long unitId, LocalDate date);
}
