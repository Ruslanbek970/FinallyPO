package kz.narxoz.hotelbooking.api;

import kz.narxoz.hotelbooking.dto.request.RoomRequestDto;
import kz.narxoz.hotelbooking.dto.response.RoomResponseDto;
import kz.narxoz.hotelbooking.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
@PreAuthorize("hasAnyRole('USER','ADMIN','MANAGER')")
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<RoomResponseDto>> getByHotel(@PathVariable Long hotelId) {
        return ResponseEntity.ok(roomService.getByHotel(hotelId));
    }

    @GetMapping("/{id}") // ✅ часто нужен в Postman
    public ResponseEntity<RoomResponseDto> getById(@PathVariable Long id) {
        RoomResponseDto room = roomService.getById(id); // ✅ сделай метод в сервисе
        if (room == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(room);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<RoomResponseDto> create(@RequestBody RoomRequestDto dto) {
        RoomResponseDto created = roomService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<RoomResponseDto> update(@PathVariable Long id, @RequestBody RoomRequestDto dto) {
        RoomResponseDto updated = roomService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/availability")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<Void> availability(@PathVariable Long id, @RequestParam boolean available) {
        Boolean result = roomService.setAvailability(id, available);
        if (!result) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok().build();
    }

    // ✅ добавлено
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = roomService.delete(id); // ✅ сделай boolean в сервисе
        if (!deleted) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.noContent().build();
    }
}
