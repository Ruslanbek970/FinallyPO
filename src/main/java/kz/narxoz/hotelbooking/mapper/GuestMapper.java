package kz.narxoz.hotelbooking.mapper;

import kz.narxoz.hotelbooking.dto.request.GuestRequestDto;
import kz.narxoz.hotelbooking.dto.response.GuestResponseDto;
import kz.narxoz.hotelbooking.model.Guest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GuestMapper {

    @Mapping(source = "booking.id", target = "bookingId")
    GuestResponseDto toDto(Guest guest);

    List<GuestResponseDto> toDtoList(List<Guest> guests);

    Guest toEntity(GuestRequestDto dto);
}
