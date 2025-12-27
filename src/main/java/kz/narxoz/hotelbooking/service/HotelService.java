package kz.narxoz.hotelbooking.service;

import kz.narxoz.hotelbooking.dto.request.HotelRequestDto;
import kz.narxoz.hotelbooking.dto.response.HotelResponseDto;

import java.util.List;

public interface HotelService {

    List<HotelResponseDto> getAll();

    HotelResponseDto getById(Long id);

    HotelResponseDto create(HotelRequestDto dto);

    HotelResponseDto update(Long id, HotelRequestDto dto);

    List<HotelResponseDto> getMyHotels();

    boolean delete(Long id);
}
