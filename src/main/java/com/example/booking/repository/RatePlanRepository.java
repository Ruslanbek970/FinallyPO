package com.example.booking.repository;

import com.example.booking.Entity.RatePlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatePlanRepository extends JpaRepository<RatePlan, Long> {
    List<RatePlan> findAllByUnit_Id(Long unitId);
    List<RatePlan> findAllByUnit_IdAndActiveTrue(Long unitId);
}
