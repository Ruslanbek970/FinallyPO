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
    public ResponseEntity<?> create(@RequestBody GuestRequestDto dto) {
        GuestResponseDto created = guestService.create(dto);
        if (created == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<?> getByBooking(@PathVariable Long bookingId) {
        List<GuestResponseDto> list = guestService.getByBooking(bookingId);
        if (list.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(list);
    }
}
