package com.example.booking.serviceImpl;

import com.example.booking.Dto.request.ReviewRequestDto;
import com.example.booking.Dto.response.ReviewResponseDto;
import com.example.booking.Entity.Booking;
import com.example.booking.Entity.Review;
import com.example.booking.Entity.User;
import com.example.booking.Enum.ReviewStatus;
import com.example.booking.Mapper.ReviewMapper;
import com.example.booking.Service.ReviewService;
import com.example.booking.repository.BookingRepository;
import com.example.booking.repository.ReviewRepository;
import com.example.booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewResponseDto create(ReviewRequestDto dto) {
        Booking booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
        User author = userRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));

        Review review = reviewMapper.toEntity(dto);
        review.setBooking(booking);
        review.setAuthor(author);

        // статус ставим автоматически (если надо)
        // review.setStatus(ReviewStatus.PENDING);

        return reviewMapper.toDto(reviewRepository.save(review));
    }

    @Override
    public ReviewResponseDto update(Long id, ReviewRequestDto dto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));

        Booking booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
        User author = userRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));

        review.setBooking(booking);
        review.setAuthor(author);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        return reviewMapper.toDto(reviewRepository.save(review));
    }

    @Override
    public ReviewResponseDto getById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
        return reviewMapper.toDto(review);
    }

    @Override
    public List<ReviewResponseDto> getAll() {
        return reviewMapper.toDtoList(reviewRepository.findAll());
    }

    @Override
    public ReviewResponseDto changeStatus(Long reviewId, ReviewStatus status) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
        review.setStatus(status);
        return reviewMapper.toDto(reviewRepository.save(review));
    }

    @Override
    public void deleteById(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }
        reviewRepository.deleteById(id);
    }
}
