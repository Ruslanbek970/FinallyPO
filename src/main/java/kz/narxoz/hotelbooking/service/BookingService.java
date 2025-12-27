package kz.narxoz.hotelbooking.service;

import kz.narxoz.hotelbooking.dto.request.BookingRequestDto;
import kz.narxoz.hotelbooking.dto.response.BookingResponseDto;

import java.util.List;

public interface BookingService {

    BookingResponseDto create(BookingRequestDto dto);

    List<BookingResponseDto> getMyBookings();

    Boolean cancel(Long bookingId);

    List<BookingResponseDto> getManagerBookings();

    Boolean confirmByManager(Long bookingId);

    Boolean cancelByManager(Long bookingId);

    BookingResponseDto getById(Long bookingId);

    boolean delete(Long bookingId);
}
