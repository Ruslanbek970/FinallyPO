package com.example.booking.controller;

import com.example.booking.Dto.request.RoleRequestDto;
import com.example.booking.Dto.response.RoleResponseDto;
import com.example.booking.Service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService service;

    @PostMapping public RoleResponseDto create(@RequestBody RoleRequestDto dto){ return service.create(dto); }
    @PutMapping("/{id}") public RoleResponseDto update(@PathVariable Long id, @RequestBody RoleRequestDto dto){ return service.update(id, dto); }
    @GetMapping("/{id}") public RoleResponseDto get(@PathVariable Long id){ return service.getById(id); }
    @GetMapping public List<RoleResponseDto> all(){ return service.getAll(); }
    @GetMapping("/by-name") public RoleResponseDto byName(@RequestParam String name){ return service.getByName(name); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id){ service.deleteById(id); }
}
