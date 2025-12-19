package com.example.booking.Dto.request;

import com.example.booking.Dto.response.AmenityResponseDto;
import com.example.booking.Enum.UnitType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitRequestDto {
    private Long propertyId;
    private UnitType unitType;
    private String nameOrNumber;
    private int capacityAdults;
    private int capacityChildren;
    private int bedsCount;
    private int baseNightPrice;
    private boolean active;

    private Set<AmenityResponseDto> amenities; // вместо amenityIds
}
