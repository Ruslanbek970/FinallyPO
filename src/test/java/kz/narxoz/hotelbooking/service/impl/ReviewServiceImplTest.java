package kz.narxoz.hotelbooking.service.impl;

import kz.narxoz.hotelbooking.dto.request.ReviewRequestDto;
import kz.narxoz.hotelbooking.dto.response.ReviewResponseDto;
import kz.narxoz.hotelbooking.mapper.ReviewMapper;
import kz.narxoz.hotelbooking.model.Hotel;
import kz.narxoz.hotelbooking.model.Review;
import kz.narxoz.hotelbooking.model.User;
import kz.narxoz.hotelbooking.repository.HotelRepository;
import kz.narxoz.hotelbooking.repository.ReviewRepository;
import kz.narxoz.hotelbooking.service.UserService;
import kz.narxoz.hotelbooking.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceImplTest {

    private ReviewRepository reviewRepository;
    private HotelRepository hotelRepository;
    private UserService userService;
    private ReviewMapper reviewMapper;

    private ReviewServiceImpl reviewService;

    @BeforeEach
    void setUp() {
        reviewRepository = mock(ReviewRepository.class);
        hotelRepository = mock(HotelRepository.class);
        userService = mock(UserService.class);
        reviewMapper = mock(ReviewMapper.class);

        reviewService = new ReviewServiceImpl(reviewRepository, hotelRepository, userService, reviewMapper);
    }

    @Test
    void create_shouldReturnNullIfNotAuth() {
        when(userService.getCurrentUserEntity()).thenReturn(null);

        assertNull(reviewService.create(new ReviewRequestDto()));
    }

    @Test
    void create_shouldReturnNullIfHotelNotFound() {
        User u = new User();
        u.setId(1L);

        ReviewRequestDto dto = new ReviewRequestDto();
        dto.setHotelId(10L);
        dto.setRating(5);

        when(userService.getCurrentUserEntity()).thenReturn(u);
        when(hotelRepository.findById(10L)).thenReturn(Optional.empty());

        assertNull(reviewService.create(dto));
    }

    @Test
    void create_shouldCreateReview() {
        User u = new User();
        u.setId(1L);

        Hotel hotel = new Hotel();
        hotel.setId(10L);

        ReviewRequestDto dto = new ReviewRequestDto();
        dto.setHotelId(10L);
        dto.setRating(5);
        dto.setComment("Nice");

        Review saved = new Review();
        saved.setId(1L);

        ReviewResponseDto responseDto = new ReviewResponseDto();
        responseDto.setId(1L);

        when(userService.getCurrentUserEntity()).thenReturn(u);
        when(hotelRepository.findById(10L)).thenReturn(Optional.of(hotel));
        when(reviewRepository.save(any(Review.class))).thenReturn(saved);
        when(reviewMapper.toDto(saved)).thenReturn(responseDto);

        ReviewResponseDto res = reviewService.create(dto);

        assertNotNull(res);
        assertEquals(1L, res.getId());
        verify(reviewRepository).save(any(Review.class));
    }
}
