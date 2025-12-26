package kz.narxoz.hotelbooking.mapper;

import kz.narxoz.hotelbooking.dto.request.AmenityRequestDto;
import kz.narxoz.hotelbooking.dto.response.AmenityResponseDto;
import kz.narxoz.hotelbooking.model.Amenity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AmenityMapper {
    Amenity toEntity(AmenityRequestDto dto);
    AmenityResponseDto toDto(Amenity amenity);
    List<AmenityResponseDto> toDtoList(List<Amenity> amenities);
}
