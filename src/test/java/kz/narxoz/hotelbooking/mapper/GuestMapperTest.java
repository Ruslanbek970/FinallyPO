package kz.narxoz.hotelbooking.mapper;

import kz.narxoz.hotelbooking.dto.request.GuestRequestDto;
import kz.narxoz.hotelbooking.dto.response.GuestResponseDto;
import kz.narxoz.hotelbooking.model.Booking;
import kz.narxoz.hotelbooking.model.Guest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class GuestMapperTest {

    private final GuestMapper guestMapper = Mappers.getMapper(GuestMapper.class);

    @Test
    void convertEntityToDto() {
        Booking booking = new Booking();
        booking.setId(10L);

        Guest guest = new Guest();
        guest.setId(1L);
        guest.setFirstName("John");
        guest.setLastName("Doe");
        guest.setDocumentNumber("KZ123");
        guest.setBooking(booking);

        GuestResponseDto dto = guestMapper.toDto(guest);

        Assertions.assertNotNull(dto);

        Assertions.assertNotNull(dto.getId());
        Assertions.assertNotNull(dto.getFirstName());
        Assertions.assertNotNull(dto.getLastName());
        Assertions.assertNotNull(dto.getDocumentNumber());
        Assertions.assertNotNull(dto.getBookingId());

        Assertions.assertEquals(guest.getId(), dto.getId());
        Assertions.assertEquals(guest.getFirstName(), dto.getFirstName());
        Assertions.assertEquals(guest.getLastName(), dto.getLastName());
        Assertions.assertEquals(guest.getDocumentNumber(), dto.getDocumentNumber());
        Assertions.assertEquals(booking.getId(), dto.getBookingId());
    }

    @Test
    void convertDtoToEntity() {
        GuestRequestDto dto = new GuestRequestDto();
        dto.setFirstName("Alice");
        dto.setLastName("Smith");
        dto.setDocumentNumber("KZ999");
        dto.setBookingId(10L);

        Guest guest = guestMapper.toEntity(dto);

        Assertions.assertNotNull(guest);

        Assertions.assertNotNull(guest.getFirstName());
        Assertions.assertNotNull(guest.getLastName());
        Assertions.assertNotNull(guest.getDocumentNumber());

        Assertions.assertEquals(dto.getFirstName(), guest.getFirstName());
        Assertions.assertEquals(dto.getLastName(), guest.getLastName());
        Assertions.assertEquals(dto.getDocumentNumber(), guest.getDocumentNumber());

        // bookingId is not mapped to Booking in mapper
        Assertions.assertNull(guest.getBooking());
    }

    @Test
    void convertEntityListToDtoList() {
        Booking booking = new Booking();
        booking.setId(10L);

        Guest g1 = new Guest();
        g1.setId(1L);
        g1.setFirstName("John");
        g1.setLastName("Doe");
        g1.setDocumentNumber("KZ123");
        g1.setBooking(booking);

        Guest g2 = new Guest();
        g2.setId(2L);
        g2.setFirstName("Alice");
        g2.setLastName("Smith");
        g2.setDocumentNumber("KZ999");
        g2.setBooking(booking);

        List<Guest> guests = new ArrayList<>();
        guests.add(g1);
        guests.add(g2);

        List<GuestResponseDto> dtoList = guestMapper.toDtoList(guests);

        Assertions.assertNotNull(dtoList);
        Assertions.assertNotEquals(0, dtoList.size());
        Assertions.assertEquals(guests.size(), dtoList.size());

        for (int i = 0; i < dtoList.size(); i++) {
            GuestResponseDto dto = dtoList.get(i);
            Guest entity = guests.get(i);

            Assertions.assertNotNull(dto);

            Assertions.assertNotNull(dto.getId());
            Assertions.assertNotNull(dto.getFirstName());
            Assertions.assertNotNull(dto.getLastName());
            Assertions.assertNotNull(dto.getDocumentNumber());
            Assertions.assertNotNull(dto.getBookingId());

            Assertions.assertEquals(entity.getId(), dto.getId());
            Assertions.assertEquals(entity.getFirstName(), dto.getFirstName());
            Assertions.assertEquals(entity.getLastName(), dto.getLastName());
            Assertions.assertEquals(entity.getDocumentNumber(), dto.getDocumentNumber());
            Assertions.assertEquals(entity.getBooking().getId(), dto.getBookingId());
        }
    }
}
