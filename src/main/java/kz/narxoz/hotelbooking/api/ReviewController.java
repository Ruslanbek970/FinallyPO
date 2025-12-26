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
    public ResponseEntity<?> create(@RequestBody ReviewRequestDto dto) {
        ReviewResponseDto created = reviewService.create(dto);
        if (created == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<?> getByHotel(@PathVariable Long hotelId) {
        List<ReviewResponseDto> list = reviewService.getByHotel(hotelId);
        if (list.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(list);
    }
}
