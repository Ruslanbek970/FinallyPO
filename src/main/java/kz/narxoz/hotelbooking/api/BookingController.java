package kz.narxoz.hotelbooking.api;

import kz.narxoz.hotelbooking.dto.request.BookingRequestDto;
import kz.narxoz.hotelbooking.dto.response.BookingResponseDto;
import kz.narxoz.hotelbooking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER','ADMIN','MANAGER')")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponseDto> create(@RequestBody BookingRequestDto dto) {
        return ResponseEntity.ok(bookingService.create(dto));
    }

    @GetMapping("/my")
    public ResponseEntity<List<BookingResponseDto>> myBookings() {
        return ResponseEntity.ok(bookingService.getMyBookings());
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Boolean> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.cancel(id));
    }
}
