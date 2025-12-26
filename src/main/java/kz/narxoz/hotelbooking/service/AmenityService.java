package kz.narxoz.hotelbooking.service;

import kz.narxoz.hotelbooking.dto.request.AmenityRequestDto;
import kz.narxoz.hotelbooking.dto.response.AmenityResponseDto;

import java.util.List;

public interface AmenityService {
    List<AmenityResponseDto> getAll();
    AmenityResponseDto create(AmenityRequestDto dto);
    AmenityResponseDto update(Long id, AmenityRequestDto dto);
    boolean delete(Long id);
}
