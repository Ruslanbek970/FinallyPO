package com.example.booking.Mapper;

import com.example.booking.Dto.AmenityRequestDto;
import com.example.booking.Dto.AmenityResponseDto;
import com.example.booking.Entity.Amenity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AmenityMapper {

    AmenityResponseDto toDto(Amenity amenity);

    Amenity toEntity(AmenityRequestDto dto);

    List<AmenityResponseDto> toDtoList(List<Amenity> amenities);
}
