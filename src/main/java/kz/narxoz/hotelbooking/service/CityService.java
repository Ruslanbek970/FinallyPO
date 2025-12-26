package kz.narxoz.hotelbooking.service;

import kz.narxoz.hotelbooking.dto.request.CityRequestDto;
import kz.narxoz.hotelbooking.dto.response.CityResponseDto;

import java.util.List;

public interface CityService {
    List<CityResponseDto> getAll();
    CityResponseDto getById(Long id);
    CityResponseDto create(CityRequestDto dto);
    CityResponseDto update(Long id, CityRequestDto dto);
    boolean delete(Long id);
}
