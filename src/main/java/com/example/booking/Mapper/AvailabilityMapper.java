package com.example.booking.Mapper;

import com.example.booking.Dto.AvailabilityRequestDto;
import com.example.booking.Dto.AvailabilityResponseDto;
import com.example.booking.Entity.Availability;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AvailabilityMapper {

    @Mapping(source = "unit.id", target = "unitId")
    AvailabilityResponseDto toDto(Availability availability);

    @Mapping(source = "unitId", target = "unit.id")
    Availability toEntity(AvailabilityRequestDto dto);

    List<AvailabilityResponseDto> toDtoList(List<Availability> availabilityList);
}
