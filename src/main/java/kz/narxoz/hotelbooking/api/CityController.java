package kz.narxoz.hotelbooking.api;

import kz.narxoz.hotelbooking.dto.request.CityRequestDto;
import kz.narxoz.hotelbooking.dto.response.CityResponseDto;
import kz.narxoz.hotelbooking.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cities")
@PreAuthorize("hasAnyRole('USER','ADMIN','MANAGER')")
public class CityController {

    private final CityService cityService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<CityResponseDto> list = cityService.getAll();
        if (list.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody CityRequestDto dto) {
        CityResponseDto created = cityService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CityRequestDto dto) {
        CityResponseDto updated = cityService.update(id, dto);
        if (updated == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = cityService.delete(id);
        if (!deleted) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok("Deleted");
    }
}
