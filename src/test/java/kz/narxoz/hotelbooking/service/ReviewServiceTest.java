package kz.narxoz.hotelbooking.service;

import kz.narxoz.hotelbooking.dto.request.ReviewRequestDto;
import kz.narxoz.hotelbooking.dto.response.ReviewResponseDto;
import kz.narxoz.hotelbooking.mapper.ReviewMapper;
import kz.narxoz.hotelbooking.model.Hotel;
import kz.narxoz.hotelbooking.model.Review;
import kz.narxoz.hotelbooking.model.User;
import kz.narxoz.hotelbooking.repository.HotelRepository;
import kz.narxoz.hotelbooking.repository.ReviewRepository;
import kz.narxoz.hotelbooking.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ReviewServiceTest {

    @Test
    void createWhenUserNull() {
        ReviewRepository reviewRepository = mock(ReviewRepository.class);
        HotelRepository hotelRepository = mock(HotelRepository.class);
        UserService userService = mock(UserService.class);
        ReviewMapper reviewMapper = mock(ReviewMapper.class);

        ReviewService reviewService = new ReviewServiceImpl(reviewRepository, hotelRepository, userService, reviewMapper);

        when(userService.getCurrentUserEntity()).thenReturn(null);

        ReviewRequestDto dto = new ReviewRequestDto();
        dto.setHotelId(1L);
        dto.setRating(5);

        ReviewResponseDto result = reviewService.create(dto);

        Assertions.assertNull(result);

        verify(userService, times(1)).getCurrentUserEntity();
        verify(reviewRepository, never()).save(any());
    }

    @Test
    void createWhenRatingInvalid() {
        ReviewRepository reviewRepository = mock(ReviewRepository.class);
        HotelRepository hotelRepository = mock(HotelRepository.class);
        UserService userService = mock(UserService.class);
        ReviewMapper reviewMapper = mock(ReviewMapper.class);

        ReviewService reviewService = new ReviewServiceImpl(reviewRepository, hotelRepository, userService, reviewMapper);

        User user = new User();
        user.setId(1L);

        when(userService.getCurrentUserEntity()).thenReturn(user);

        Hotel hotel = new Hotel();
        hotel.setId(10L);

        when(hotelRepository.findById(10L)).thenReturn(Optional.of(hotel));

        ReviewRequestDto dto = new ReviewRequestDto();
        dto.setHotelId(10L);
        dto.setRating(10);

        ReviewResponseDto result = reviewService.create(dto);

        Assertions.assertNull(result);

        verify(reviewRepository, never()).save(any());
    }

    @Test
    void createSuccess() {
        ReviewRepository reviewRepository = mock(ReviewRepository.class);
        HotelRepository hotelRepository = mock(HotelRepository.class);
        UserService userService = mock(UserService.class);
        ReviewMapper reviewMapper = mock(ReviewMapper.class);

        ReviewService reviewService = new ReviewServiceImpl(reviewRepository, hotelRepository, userService, reviewMapper);

        User user = new User();
        user.setId(1L);

        when(userService.getCurrentUserEntity()).thenReturn(user);

        Hotel hotel = new Hotel();
        hotel.setId(10L);

        when(hotelRepository.findById(10L)).thenReturn(Optional.of(hotel));

        Review saved = new Review();
        saved.setId(5L);

        when(reviewRepository.save(any(Review.class))).thenReturn(saved);

        ReviewResponseDto response = new ReviewResponseDto();
        response.setId(5L);

        when(reviewMapper.toDto(saved)).thenReturn(response);

        ReviewRequestDto dto = new ReviewRequestDto();
        dto.setHotelId(10L);
        dto.setRating(5);
        dto.setComment("Great");

        ReviewResponseDto result = reviewService.create(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(5L, result.getId());

        verify(reviewRepository, times(1)).save(any(Review.class));
        verify(reviewMapper, times(1)).toDto(saved);
    }

    @Test
    void getByHotelWhenHotelNotFound() {
        ReviewRepository reviewRepository = mock(ReviewRepository.class);
        HotelRepository hotelRepository = mock(HotelRepository.class);
        UserService userService = mock(UserService.class);
        ReviewMapper reviewMapper = mock(ReviewMapper.class);

        ReviewService reviewService = new ReviewServiceImpl(reviewRepository, hotelRepository, userService, reviewMapper);

        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        List<ReviewResponseDto> result = reviewService.getByHotel(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());

        verify(hotelRepository, times(1)).findById(1L);
        verify(reviewRepository, never()).findAllByHotel(any());
    }
}
