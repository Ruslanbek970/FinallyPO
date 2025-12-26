package com.example.booking.controller;

import com.example.booking.Dto.request.UnitRequestDto;
import com.example.booking.Dto.response.UnitResponseDto;
import com.example.booking.Service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/units")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService service;

    @PostMapping public UnitResponseDto create(@RequestBody UnitRequestDto dto){ return service.create(dto); }
    @PutMapping("/{id}") public UnitResponseDto update(@PathVariable Long id, @RequestBody UnitRequestDto dto){ return service.update(id, dto); }
    @GetMapping("/{id}") public UnitResponseDto get(@PathVariable Long id){ return service.getById(id); }
    @GetMapping public List<UnitResponseDto> all(){ return service.getAll(); }

    @GetMapping("/by-property")
    public List<UnitResponseDto> byProperty(@RequestParam Long propertyId){ return service.getByPropertyId(propertyId); }

    @PatchMapping("/{id}/amenities")
    public UnitResponseDto updateAmenities(@PathVariable Long id, @RequestBody Set<Long> amenityIds) {
        return service.updateAmenities(id, amenityIds);
    }

    @DeleteMapping("/{id}") public void delete(@PathVariable Long id){ service.deleteById(id); }
}
