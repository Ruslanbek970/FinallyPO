package kz.narxoz.hotelbooking.mapper;

import kz.narxoz.hotelbooking.dto.request.BookingRequestDto;
import kz.narxoz.hotelbooking.dto.response.BookingResponseDto;
import kz.narxoz.hotelbooking.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "userEmail")
    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "room.roomNumber", target = "roomNumber")
    @Mapping(source = "room.hotel.name", target = "hotelName")
    BookingResponseDto toDto(Booking booking);

    List<BookingResponseDto> toDtoList(List<Booking> bookings);

    Booking toEntity(BookingRequestDto dto);
}
