package com.example.booking.Mapper;

import com.example.booking.Dto.UnitRequestDto;
import com.example.booking.Dto.UnitResponseDto;
import com.example.booking.Entity.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UnitMapper {

    @Mapping(source = "property.id", target = "propertyId")
    UnitResponseDto toDto(Unit unit);

    @Mapping(source = "propertyId", target = "property.id")
    Unit toEntity(UnitRequestDto dto);

    List<UnitResponseDto> toDtoList(List<Unit> units);
}
