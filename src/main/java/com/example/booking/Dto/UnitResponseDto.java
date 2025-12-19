package com.example.booking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitResponseDto {
    private Long id;
    private Long propertyId;
    private String unitType;
    private String nameOrNumber;
    private int capacityAdults;
    private int capacityChildren;
    private int bedsCount;
    private int baseNightPrice;
    private boolean active;
    private Set<Long> amenityIds;
}
