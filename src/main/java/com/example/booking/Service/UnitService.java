package com.example.booking.Service;

import com.example.booking.Dto.request.UnitRequestDto;
import com.example.booking.Dto.response.UnitResponseDto;

import java.util.List;
import java.util.Set;

public interface UnitService {

    UnitResponseDto create(UnitRequestDto dto);
    UnitResponseDto update(Long id, UnitRequestDto dto);
    UnitResponseDto updateAmenities(Long unitId, Set<Long> amenityIds);
    UnitResponseDto getById(Long id);
    List<UnitResponseDto> getAll();
    List<UnitResponseDto> getByPropertyId(Long propertyId);
    void deleteById(Long id);
}
