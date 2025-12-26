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
    public ResponseEntity<?> getAll() {
        List<AmenityResponseDto> list = amenityService.getAll();
        if (list.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody AmenityRequestDto dto) {
        AmenityResponseDto created = amenityService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AmenityRequestDto dto) {
        AmenityResponseDto updated = amenityService.update(id, dto);
        if (updated == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = amenityService.delete(id);
        if (!deleted) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok("Deleted");
    }
}
