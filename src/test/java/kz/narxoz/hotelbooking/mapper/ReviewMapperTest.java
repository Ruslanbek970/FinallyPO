package kz.narxoz.hotelbooking.mapper;

import kz.narxoz.hotelbooking.dto.request.ReviewRequestDto;
import kz.narxoz.hotelbooking.dto.response.ReviewResponseDto;
import kz.narxoz.hotelbooking.model.Hotel;
import kz.narxoz.hotelbooking.model.Review;
import kz.narxoz.hotelbooking.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReviewMapperTest {

    private final ReviewMapper reviewMapper = Mappers.getMapper(ReviewMapper.class);

    @Test
    void convertEntityToDto() {
        User user = new User();
        user.setId(10L);
        user.setEmail("user@test.com");

        Hotel hotel = new Hotel();
        hotel.setId(20L);
        hotel.setName("Test Hotel");

        Review review = new Review();
        review.setId(1L);
        review.setUser(user);
        review.setHotel(hotel);
        review.setRating(5);
        review.setComment("Great!");
        review.setCreatedAt(LocalDateTime.of(2026, 1, 1, 10, 0));

        ReviewResponseDto dto = reviewMapper.toDto(review);

        Assertions.assertNotNull(dto);

        Assertions.assertNotNull(dto.getId());
        Assertions.assertNotNull(dto.getUserId());
        Assertions.assertNotNull(dto.getUserEmail());
        Assertions.assertNotNull(dto.getHotelId());
        Assertions.assertNotNull(dto.getHotelName());
        Assertions.assertNotNull(dto.getCreatedAt());

        Assertions.assertEquals(review.getId(), dto.getId());
        Assertions.assertEquals(review.getRating(), dto.getRating());
        Assertions.assertEquals(review.getComment(), dto.getComment());
        Assertions.assertEquals(review.getCreatedAt(), dto.getCreatedAt());

        Assertions.assertEquals(user.getId(), dto.getUserId());
        Assertions.assertEquals(user.getEmail(), dto.getUserEmail());

        Assertions.assertEquals(hotel.getId(), dto.getHotelId());
        Assertions.assertEquals(hotel.getName(), dto.getHotelName());
    }

    @Test
    void convertDtoToEntity() {
        ReviewRequestDto dto = new ReviewRequestDto();
        dto.setHotelId(20L);
        dto.setRating(4);
        dto.setComment("Ok");

        Review review = reviewMapper.toEntity(dto);

        Assertions.assertNotNull(review);
        Assertions.assertEquals(dto.getRating(), review.getRating());
        Assertions.assertEquals(dto.getComment(), review.getComment());

        // hotelId is not mapped to Hotel in mapper
        Assertions.assertNull(review.getHotel());
        Assertions.assertNull(review.getUser());
    }

    @Test
    void convertEntityListToDtoList() {
        User user = new User();
        user.setId(10L);
        user.setEmail("user@test.com");

        Hotel hotel = new Hotel();
        hotel.setId(20L);
        hotel.setName("Test Hotel");

        Review r1 = new Review();
        r1.setId(1L);
        r1.setUser(user);
        r1.setHotel(hotel);
        r1.setRating(5);
        r1.setComment("Great!");
        r1.setCreatedAt(LocalDateTime.of(2026, 1, 1, 10, 0));

        Review r2 = new Review();
        r2.setId(2L);
        r2.setUser(user);
        r2.setHotel(hotel);
        r2.setRating(4);
        r2.setComment("Ok");
        r2.setCreatedAt(LocalDateTime.of(2026, 1, 2, 10, 0));

        List<Review> reviews = new ArrayList<>();
        reviews.add(r1);
        reviews.add(r2);

        List<ReviewResponseDto> dtoList = reviewMapper.toDtoList(reviews);

        Assertions.assertNotNull(dtoList);
        Assertions.assertNotEquals(0, dtoList.size());
        Assertions.assertEquals(reviews.size(), dtoList.size());

        for (int i = 0; i < dtoList.size(); i++) {
            ReviewResponseDto dto = dtoList.get(i);
            Review entity = reviews.get(i);

            Assertions.assertNotNull(dto);
            Assertions.assertNotNull(dto.getId());
            Assertions.assertNotNull(dto.getUserId());
            Assertions.assertNotNull(dto.getHotelId());

            Assertions.assertEquals(entity.getId(), dto.getId());
            Assertions.assertEquals(entity.getUser().getId(), dto.getUserId());
            Assertions.assertEquals(entity.getHotel().getId(), dto.getHotelId());
        }
    }
}
