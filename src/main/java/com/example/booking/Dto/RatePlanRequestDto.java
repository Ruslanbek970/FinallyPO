package com.example.booking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatePlanRequestDto {
    private Long unitId;
    private String name;
    private int pricePerNight;
    private String cancellationPolicy;
    private boolean breakfastIncluded;
    private boolean active;
}
