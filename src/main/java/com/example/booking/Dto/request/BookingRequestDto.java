package com.example.booking.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDto {
    private Long guestId;
    private Long unitId;
    private Long ratePlanId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int adults;
    private int children;
    private String guestComment;
}
