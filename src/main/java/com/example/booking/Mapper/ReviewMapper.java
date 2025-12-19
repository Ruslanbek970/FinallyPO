package com.example.booking.Mapper;

import com.example.booking.Dto.ReviewRequestDto;
import com.example.booking.Dto.ReviewResponseDto;
import com.example.booking.Entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "booking.id", target = "bookingId")
    @Mapping(source = "author.id", target = "authorId")
    ReviewResponseDto toDto(Review review);

    @Mapping(source = "bookingId", target = "booking.id")
    @Mapping(source = "authorId", target = "author.id")
    Review toEntity(ReviewRequestDto dto);

    List<ReviewResponseDto> toDtoList(List<Review> reviews);
}
