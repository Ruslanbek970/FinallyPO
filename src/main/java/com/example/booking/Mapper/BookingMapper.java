package com.example.booking.Mapper;

import com.example.booking.Dto.request.BookingRequestDto;
import com.example.booking.Dto.response.BookingResponseDto;
import com.example.booking.Entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "guest.id", target = "guestId")
    @Mapping(source = "unit.id", target = "unitId")
    @Mapping(source = "ratePlan.id", target = "ratePlanId")
    BookingResponseDto toDto(Booking booking);

    @Mapping(source = "guestId", target = "guest.id")
    @Mapping(source = "unitId", target = "unit.id")
    @Mapping(source = "ratePlanId", target = "ratePlan.id")
    Booking toEntity(BookingRequestDto dto);

    List<BookingResponseDto> toDtoList(List<Booking> bookings);
}
