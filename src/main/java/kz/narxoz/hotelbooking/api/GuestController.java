package kz.narxoz.hotelbooking.api;

import kz.narxoz.hotelbooking.dto.request.GuestRequestDto;
import kz.narxoz.hotelbooking.dto.response.GuestResponseDto;
import kz.narxoz.hotelbooking.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/guests")
@PreAuthorize("hasAnyRole('USER','ADMIN','MANAGER')")
public class GuestController {

    private final GuestService guestService;

    @PostMapping
    public ResponseEntity<GuestResponseDto> create(@RequestBody GuestRequestDto dto) {
        GuestResponseDto created = guestService.create(dto);
        if (created == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<GuestResponseDto>> getByBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(guestService.getByBooking(bookingId));
    }
}
