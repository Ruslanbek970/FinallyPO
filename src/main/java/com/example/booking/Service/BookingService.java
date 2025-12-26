package com.example.booking.Service;

import com.example.booking.Dto.request.BookingRequestDto;
import com.example.booking.Dto.response.BookingResponseDto;
import com.example.booking.Enum.BookingStatus;

import java.util.List;

public interface BookingService {

    BookingResponseDto create(BookingRequestDto dto);

    BookingResponseDto getById(Long id);

    List<BookingResponseDto> getAll();

    List<BookingResponseDto> getByGuestId(Long guestId);

    List<BookingResponseDto> getByUnitId(Long unitId);

    BookingResponseDto changeStatus(Long bookingId, BookingStatus status); // CREATED/CONFIRMED/CANCELLED/COMPLETED

    void deleteById(Long id);
}
