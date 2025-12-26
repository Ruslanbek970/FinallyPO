package com.example.booking.controller;

import com.example.booking.Dto.request.PaymentRequestDto;
import com.example.booking.Dto.response.PaymentResponseDto;
import com.example.booking.Enum.PaymentStatus;
import com.example.booking.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping public PaymentResponseDto create(@RequestBody PaymentRequestDto dto){ return service.create(dto); }
    @GetMapping("/{id}") public PaymentResponseDto get(@PathVariable Long id){ return service.getById(id); }
    @GetMapping public List<PaymentResponseDto> all(){ return service.getAll(); }

    @GetMapping("/by-booking") public List<PaymentResponseDto> byBooking(@RequestParam Long bookingId){ return service.getByBookingId(bookingId); }

    @PatchMapping("/{id}/status")
    public PaymentResponseDto changeStatus(@PathVariable Long id, @RequestParam PaymentStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}") public void delete(@PathVariable Long id){ service.deleteById(id); }
}
