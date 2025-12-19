package com.example.booking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.booking.Enum.UnitType;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitResponseDto {
    private Long id;
    private Long propertyId;
    private UnitType unitType;
    private String nameOrNumber;
    private int capacityAdults;
    private int capacityChildren;
    private int bedsCount;
    private int baseNightPrice;
    private boolean active;
    private Set<Long> amenityIds;
}