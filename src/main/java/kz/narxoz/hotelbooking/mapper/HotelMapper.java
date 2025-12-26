package kz.narxoz.hotelbooking.mapper;

import kz.narxoz.hotelbooking.dto.request.HotelRequestDto;
import kz.narxoz.hotelbooking.dto.response.HotelResponseDto;
import kz.narxoz.hotelbooking.model.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")
    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "manager.fullName", target = "managerFullName")
    HotelResponseDto toDto(Hotel hotel);

    List<HotelResponseDto> toDtoList(List<Hotel> hotels);

    Hotel toEntity(HotelRequestDto dto);
}
