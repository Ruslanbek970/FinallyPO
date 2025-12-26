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
    public ResponseEntity<?> getByHotel(@PathVariable Long hotelId) {
        List<RoomResponseDto> rooms = roomService.getByHotel(hotelId);
        if (rooms.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(rooms);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<?> create(@RequestBody RoomRequestDto dto) {
        RoomResponseDto created = roomService.create(dto);
        if (created == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody RoomRequestDto dto) {
        RoomResponseDto updated = roomService.update(id, dto);
        if (updated == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/availability")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<?> availability(@PathVariable Long id, @RequestParam boolean available) {
        Boolean result = roomService.setAvailability(id, available);
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok("Updated");
    }
}
