package com.example.booking.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.booking.Enum.PaymentMethod;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {
    private Long bookingId;
    private int amount;
    private PaymentMethod method;
    private String provider;
}