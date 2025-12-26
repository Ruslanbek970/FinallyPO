package com.example.booking.controller;

import com.example.booking.Dto.request.BookingRequestDto;
import com.example.booking.Dto.response.BookingResponseDto;
import com.example.booking.Enum.BookingStatus;
import com.example.booking.Service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService service;

    @PostMapping public BookingResponseDto create(@RequestBody BookingRequestDto dto){ return service.create(dto); }
    @GetMapping("/{id}") public BookingResponseDto get(@PathVariable Long id){ return service.getById(id); }
    @GetMapping public List<BookingResponseDto> all(){ return service.getAll(); }

    @GetMapping("/by-guest") public List<BookingResponseDto> byGuest(@RequestParam Long guestId){ return service.getByGuestId(guestId); }
    @GetMapping("/by-unit") public List<BookingResponseDto> byUnit(@RequestParam Long unitId){ return service.getByUnitId(unitId); }

    @PatchMapping("/{id}/status")
    public BookingResponseDto changeStatus(@PathVariable Long id, @RequestParam BookingStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}") public void delete(@PathVariable Long id){ service.deleteById(id); }
}
