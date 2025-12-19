package com.example.booking.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityResponseDto {
    private Long id;
    private Long unitId;
    private LocalDate date;
    private int availableCount;
    private Integer overridePrice;
    private boolean stopSell;
}
