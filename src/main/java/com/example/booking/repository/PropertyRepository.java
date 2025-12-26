package com.example.booking.repository;

import com.example.booking.Entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByOwnerId(Long ownerId);
    List<Property> findByCityIgnoreCase(String city);
}
