package kz.narxoz.hotelbooking.service;

import kz.narxoz.hotelbooking.dto.request.GuestRequestDto;
import kz.narxoz.hotelbooking.dto.response.GuestResponseDto;

import java.util.List;

public interface GuestService {
    GuestResponseDto create(GuestRequestDto dto);
    List<GuestResponseDto> getByBooking(Long bookingId);
}
