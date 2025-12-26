package kz.narxoz.hotelbooking.mapper;

import kz.narxoz.hotelbooking.dto.request.CityRequestDto;
import kz.narxoz.hotelbooking.dto.response.CityResponseDto;
import kz.narxoz.hotelbooking.model.City;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {
    City toEntity(CityRequestDto dto);
    CityResponseDto toDto(City city);
    List<CityResponseDto> toDtoList(List<City> cities);
}
