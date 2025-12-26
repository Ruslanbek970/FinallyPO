package com.example.booking.Service;

import com.example.booking.Dto.request.PropertyRequestDto;
import com.example.booking.Dto.response.PropertyResponseDto;

import java.util.List;

public interface PropertyService {

    PropertyResponseDto create(PropertyRequestDto dto);

    PropertyResponseDto update(Long id, PropertyRequestDto dto);

    PropertyResponseDto getById(Long id);

    List<PropertyResponseDto> getAll();

    List<PropertyResponseDto> getByOwnerId(Long ownerId);

    List<PropertyResponseDto> getByCity(String city);

    void deleteById(Long id);
}
