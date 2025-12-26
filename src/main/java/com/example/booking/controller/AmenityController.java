package com.example.booking.controller;

import com.example.booking.Dto.request.AmenityRequestDto;
import com.example.booking.Dto.response.AmenityResponseDto;
import com.example.booking.Service.AmenityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/amenities")
@RequiredArgsConstructor
public class AmenityController {

    private final AmenityService service;

    @PostMapping public AmenityResponseDto create(@RequestBody AmenityRequestDto dto){ return service.create(dto); }
    @PutMapping("/{id}") public AmenityResponseDto update(@PathVariable Long id, @RequestBody AmenityRequestDto dto){ return service.update(id, dto); }
    @GetMapping("/{id}") public AmenityResponseDto get(@PathVariable Long id){ return service.getById(id); }
    @GetMapping public List<AmenityResponseDto> all(){ return service.getAll(); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id){ service.deleteById(id); }
}
