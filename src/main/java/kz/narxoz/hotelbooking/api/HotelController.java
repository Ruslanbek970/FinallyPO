package kz.narxoz.hotelbooking.api;

import kz.narxoz.hotelbooking.dto.request.HotelRequestDto;
import kz.narxoz.hotelbooking.dto.response.HotelResponseDto;
import kz.narxoz.hotelbooking.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER','ADMIN','MANAGER')")
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    public ResponseEntity<List<HotelResponseDto>> getAll() {
        return ResponseEntity.ok(hotelService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<HotelResponseDto> create(@RequestBody HotelRequestDto dto) {
        return ResponseEntity.ok(hotelService.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<HotelResponseDto> update(@PathVariable Long id, @RequestBody HotelRequestDto dto) {
        return ResponseEntity.ok(hotelService.update(id, dto));
    }
}
