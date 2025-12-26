package kz.narxoz.hotelbooking.api;

import kz.narxoz.hotelbooking.dto.response.BookingResponseDto;
import kz.narxoz.hotelbooking.dto.response.HotelResponseDto;
import kz.narxoz.hotelbooking.service.BookingService;
import kz.narxoz.hotelbooking.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
@RequiredArgsConstructor
@PreAuthorize("hasRole('MANAGER')")
public class ManagerController {

    private final BookingService bookingService;
    private final HotelService hotelService;

    // менеджер видит только свои отели (реализуем в сервисе)
    @GetMapping("/hotels")
    public ResponseEntity<List<HotelResponseDto>> myHotels() {
        return ResponseEntity.ok(hotelService.getMyHotels());
    }

    // менеджер видит брони по своим отелям
    @GetMapping("/bookings")
    public ResponseEntity<List<BookingResponseDto>> myHotelsBookings() {
        return ResponseEntity.ok(bookingService.getManagerBookings());
    }

    // подтверждение брони
    @PutMapping("/bookings/{id}/confirm")
    public ResponseEntity<Boolean> confirm(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.confirmByManager(id));
    }

    // отмена брони менеджером
    @PutMapping("/bookings/{id}/cancel")
    public ResponseEntity<Boolean> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.cancelByManager(id));
    }
}
