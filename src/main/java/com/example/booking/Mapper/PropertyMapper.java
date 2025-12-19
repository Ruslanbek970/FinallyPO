package com.example.booking.Mapper;

import com.example.booking.Dto.PropertyRequestDto;
import com.example.booking.Dto.PropertyResponseDto;
import com.example.booking.Entity.Property;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PropertyMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    PropertyResponseDto toDto(Property property);

    @Mapping(source = "ownerId", target = "owner.id")
    Property toEntity(PropertyRequestDto dto);

    List<PropertyResponseDto> toDtoList(List<Property> properties);
}
