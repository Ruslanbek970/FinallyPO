package com.example.booking.controller;

import com.example.booking.Dto.request.ReviewRequestDto;
import com.example.booking.Dto.response.ReviewResponseDto;
import com.example.booking.Enum.ReviewStatus;
import com.example.booking.Service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    @PostMapping public ReviewResponseDto create(@RequestBody ReviewRequestDto dto){ return service.create(dto); }
    @PutMapping("/{id}") public ReviewResponseDto update(@PathVariable Long id, @RequestBody ReviewRequestDto dto){ return service.update(id, dto); }
    @GetMapping("/{id}") public ReviewResponseDto get(@PathVariable Long id){ return service.getById(id); }
    @GetMapping public List<ReviewResponseDto> all(){ return service.getAll(); }

    @PatchMapping("/{id}/status")
    public ReviewResponseDto changeStatus(@PathVariable Long id, @RequestParam ReviewStatus status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}") public void delete(@PathVariable Long id){ service.deleteById(id); }
}
