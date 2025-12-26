package kz.narxoz.hotelbooking.service;

import kz.narxoz.hotelbooking.dto.request.RoomRequestDto;
import kz.narxoz.hotelbooking.dto.response.RoomResponseDto;

import java.util.List;

public interface RoomService {
    List<RoomResponseDto> getByHotel(Long hotelId);
    RoomResponseDto create(RoomRequestDto dto);
    RoomResponseDto update(Long id, RoomRequestDto dto);
    Boolean setAvailability(Long id, boolean available);
}
