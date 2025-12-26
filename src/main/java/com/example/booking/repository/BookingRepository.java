package com.example.booking.repository;

import com.example.booking.Entity.Booking;
import com.example.booking.Enum.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByBookingCode(String bookingCode);

    List<Booking> findAllByGuest_Id(Long guestId);
    List<Booking> findAllByUnit_Id(Long unitId);

    List<Booking> findAllByStatus(BookingStatus status);

    // пересекающиеся брони по юниту (если надо проверять доступность)
    List<Booking> findAllByUnit_IdAndStatusInAndCheckInLessThanAndCheckOutGreaterThan(
            Long unitId,
            List<BookingStatus> statuses,
            LocalDate checkOut,
            LocalDate checkIn
    );
}
