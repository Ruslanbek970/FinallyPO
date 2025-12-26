package com.example.booking.repository;

import com.example.booking.Entity.Property;
import com.example.booking.Enum.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findAllByOwner_Id(Long ownerId);
    List<Property> findAllByCityIgnoreCase(String city);
    List<Property> findAllByType(PropertyType type);
}
