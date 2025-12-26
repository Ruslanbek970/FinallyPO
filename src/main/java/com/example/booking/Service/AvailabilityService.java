package com.example.booking.Service;

import com.example.booking.Dto.request.AvailabilityRequestDto;
import com.example.booking.Dto.response.AvailabilityResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityService {
    AvailabilityResponseDto create(AvailabilityRequestDto dto);
    AvailabilityResponseDto update(Long id, AvailabilityRequestDto dto);
    AvailabilityResponseDto getById(Long id);
    List<AvailabilityResponseDto> getAll();
    List<AvailabilityResponseDto> getByUnitAndDates(Long unitId, LocalDate from, LocalDate to);
    void deleteById(Long id);
}
