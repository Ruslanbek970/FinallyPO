package kz.narxoz.hotelbooking.mapper;

import kz.narxoz.hotelbooking.dto.request.ReviewRequestDto;
import kz.narxoz.hotelbooking.dto.response.ReviewResponseDto;
import kz.narxoz.hotelbooking.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "userEmail")
    @Mapping(source = "hotel.id", target = "hotelId")
    @Mapping(source = "hotel.name", target = "hotelName")
    ReviewResponseDto toDto(Review review);

    List<ReviewResponseDto> toDtoList(List<Review> reviews);

    Review toEntity(ReviewRequestDto dto);
}
