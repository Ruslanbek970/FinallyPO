package com.example.booking.Service;

import com.example.booking.Dto.request.ReviewRequestDto;
import com.example.booking.Dto.response.ReviewResponseDto;
import com.example.booking.Enum.ReviewStatus;

import java.util.List;

public interface ReviewService {
    ReviewResponseDto create(ReviewRequestDto dto);
    ReviewResponseDto update(Long id, ReviewRequestDto dto);
    ReviewResponseDto getById(Long id);
    List<ReviewResponseDto> getAll();
    ReviewResponseDto changeStatus(Long reviewId, ReviewStatus status);
    void deleteById(Long id);
}
