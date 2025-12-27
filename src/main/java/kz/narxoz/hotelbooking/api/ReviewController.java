package kz.narxoz.hotelbooking.api;

import kz.narxoz.hotelbooking.dto.request.ReviewRequestDto;
import kz.narxoz.hotelbooking.dto.response.ReviewResponseDto;
import kz.narxoz.hotelbooking.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
@PreAuthorize("hasAnyRole('USER','ADMIN','MANAGER')")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDto> create(@RequestBody ReviewRequestDto dto) {
        ReviewResponseDto created = reviewService.create(dto);
        if (created == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<ReviewResponseDto>> getByHotel(@PathVariable Long hotelId) {
        return ResponseEntity.ok(reviewService.getByHotel(hotelId));
    }
}
