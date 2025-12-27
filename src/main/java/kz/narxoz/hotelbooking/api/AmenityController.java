package kz.narxoz.hotelbooking.api;

import kz.narxoz.hotelbooking.dto.request.AmenityRequestDto;
import kz.narxoz.hotelbooking.dto.response.AmenityResponseDto;
import kz.narxoz.hotelbooking.service.AmenityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/amenities")
@PreAuthorize("hasAnyRole('USER','ADMIN','MANAGER')")
public class AmenityController {

    private final AmenityService amenityService;

    @GetMapping
    public ResponseEntity<List<AmenityResponseDto>> getAll() {
        return ResponseEntity.ok(amenityService.getAll());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AmenityResponseDto> create(@RequestBody AmenityRequestDto dto) {
        AmenityResponseDto created = amenityService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AmenityResponseDto> update(@PathVariable Long id, @RequestBody AmenityRequestDto dto) {
        AmenityResponseDto updated = amenityService.update(id, dto);
        if (updated == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = amenityService.delete(id);
        if (!deleted) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.noContent().build();
    }
}
