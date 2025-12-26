package kz.narxoz.hotelbooking.service;

import kz.narxoz.hotelbooking.dto.request.ReviewRequestDto;
import kz.narxoz.hotelbooking.dto.response.ReviewResponseDto;

import java.util.List;

public interface ReviewService {
    ReviewResponseDto create(ReviewRequestDto dto);
    List<ReviewResponseDto> getByHotel(Long hotelId);
}
