package com.example.booking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityRequestDto {
    private Long unitId;
    private LocalDate date;
    private int availableCount;
    private Integer overridePrice;
    private boolean stopSell;
}
