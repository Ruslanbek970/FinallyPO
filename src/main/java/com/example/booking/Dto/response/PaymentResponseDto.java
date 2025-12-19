package com.example.booking.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.booking.Enum.PaymentMethod;
import com.example.booking.Enum.PaymentStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDto {
    private Long id;
    private Long bookingId;
    private int amount;
    private PaymentMethod method;
    private PaymentStatus status;
    private String provider;
    private LocalDateTime paidAt;
}