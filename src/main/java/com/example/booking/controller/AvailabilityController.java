package com.example.booking.controller;

import com.example.booking.Dto.request.AvailabilityRequestDto;
import com.example.booking.Dto.response.AvailabilityResponseDto;
import com.example.booking.Service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService service;

    @PostMapping public AvailabilityResponseDto create(@RequestBody AvailabilityRequestDto dto){ return service.create(dto); }
    @PutMapping("/{id}") public AvailabilityResponseDto update(@PathVariable Long id, @RequestBody AvailabilityRequestDto dto){ return service.update(id, dto); }
    @GetMapping("/{id}") public AvailabilityResponseDto get(@PathVariable Long id){ return service.getById(id); }
    @GetMapping public List<AvailabilityResponseDto> all(){ return service.getAll(); }

    @GetMapping("/by-unit")
    public List<AvailabilityResponseDto> byUnit(
            @RequestParam Long unitId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return service.getByUnitAndDates(unitId, from, to);
    }

    @DeleteMapping("/{id}") public void delete(@PathVariable Long id){ service.deleteById(id); }
}
