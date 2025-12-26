package com.example.booking.controller;

import com.example.booking.Dto.request.PropertyRequestDto;
import com.example.booking.Dto.response.PropertyResponseDto;
import com.example.booking.Service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService service;

    @PostMapping public PropertyResponseDto create(@RequestBody PropertyRequestDto dto){ return service.create(dto); }
    @PutMapping("/{id}") public PropertyResponseDto update(@PathVariable Long id, @RequestBody PropertyRequestDto dto){ return service.update(id, dto); }
    @GetMapping("/{id}") public PropertyResponseDto get(@PathVariable Long id){ return service.getById(id); }
    @GetMapping public List<PropertyResponseDto> all(){ return service.getAll(); }

    @GetMapping("/by-owner") public List<PropertyResponseDto> byOwner(@RequestParam Long ownerId){ return service.getByOwnerId(ownerId); }
    @GetMapping("/by-city") public List<PropertyResponseDto> byCity(@RequestParam String city){ return service.getByCity(city); }

    @DeleteMapping("/{id}") public void delete(@PathVariable Long id){ service.deleteById(id); }
}
