package com.example.booking.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.booking.Enum.CancellationPolicy;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatePlanResponseDto {
    private Long id;
    private Long unitId;
    private String name;
    private int pricePerNight;
    private CancellationPolicy cancellationPolicy;
    private boolean breakfastIncluded;
    private boolean active;
}