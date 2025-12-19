package com.example.booking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.booking.Enum.BookingStatus;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDto {
    private Long id;
    private String bookingCode;
    private Long guestId;
    private Long unitId;
    private Long ratePlanId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int adults;
    private int children;
    private BookingStatus status;
    private int totalPrice;
}