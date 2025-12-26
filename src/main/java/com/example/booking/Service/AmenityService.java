package com.example.booking.Service;

import com.example.booking.Dto.request.AmenityRequestDto;
import com.example.booking.Dto.response.AmenityResponseDto;

import java.util.List;

public interface AmenityService {

    AmenityResponseDto create(AmenityRequestDto dto);

    AmenityResponseDto update(Long id, AmenityRequestDto dto);

    AmenityResponseDto getById(Long id);

    List<AmenityResponseDto> getAll();

    void deleteById(Long id);
}
