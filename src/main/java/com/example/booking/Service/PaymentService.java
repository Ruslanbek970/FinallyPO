package com.example.booking.Service;

import com.example.booking.Dto.request.PaymentRequestDto;
import com.example.booking.Dto.response.PaymentResponseDto;
import com.example.booking.Enum.PaymentStatus;

import java.util.List;

public interface PaymentService {
    PaymentResponseDto create(PaymentRequestDto dto);
    PaymentResponseDto getById(Long id);
    List<PaymentResponseDto> getAll();
    List<PaymentResponseDto> getByBookingId(Long bookingId);
    PaymentResponseDto changeStatus(Long paymentId, PaymentStatus status);
    void deleteById(Long id);
}
