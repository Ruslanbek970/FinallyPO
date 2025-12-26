package kz.narxoz.hotelbooking.service.impl;

import kz.narxoz.hotelbooking.dto.request.ReviewRequestDto;
import kz.narxoz.hotelbooking.dto.response.ReviewResponseDto;
import kz.narxoz.hotelbooking.mapper.ReviewMapper;
import kz.narxoz.hotelbooking.model.Hotel;
import kz.narxoz.hotelbooking.model.Review;
import kz.narxoz.hotelbooking.model.User;
import kz.narxoz.hotelbooking.repository.HotelRepository;
import kz.narxoz.hotelbooking.repository.ReviewRepository;
import kz.narxoz.hotelbooking.service.ReviewService;
import kz.narxoz.hotelbooking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final HotelRepository hotelRepository;
    private final UserService userService;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewResponseDto create(ReviewRequestDto dto) {
        User user = userService.getCurrentUserEntity();
        if (user == null) return null;

        Hotel hotel = hotelRepository.findById(dto.getHotelId()).orElse(null);
        if (hotel == null) return null;

        if (dto.getRating() < 1 || dto.getRating() > 5) return null;

        Review r = new Review();
        r.setHotel(hotel);
        r.setUser(user);
        r.setRating(dto.getRating());
        r.setComment(dto.getComment());

        Review saved = reviewRepository.save(r);
        return reviewMapper.toDto(saved);
    }

    @Override
    public List<ReviewResponseDto> getByHotel(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElse(null);
        if (Objects.isNull(hotel)) return List.of();
        return reviewMapper.toDtoList(reviewRepository.findAllByHotel(hotel));
    }
}
