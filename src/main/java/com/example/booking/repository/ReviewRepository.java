package com.example.booking.repository;

import com.example.booking.Entity.Review;
import com.example.booking.Enum.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByBooking_Id(Long bookingId);
    List<Review> findAllByAuthor_Id(Long authorId);
    List<Review> findAllByStatus(ReviewStatus status);
}
