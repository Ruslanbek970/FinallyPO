package kz.narxoz.hotelbooking.mapper;

import kz.narxoz.hotelbooking.dto.request.BookingRequestDto;
import kz.narxoz.hotelbooking.dto.response.BookingResponseDto;
import kz.narxoz.hotelbooking.model.Booking;
import kz.narxoz.hotelbooking.model.Hotel;
import kz.narxoz.hotelbooking.model.Room;
import kz.narxoz.hotelbooking.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingMapperTest {

    private final BookingMapper bookingMapper = Mappers.getMapper(BookingMapper.class);

    @Test
    void convertEntityToDto() {
        User user = new User();
        user.setId(10L);
        user.setEmail("user@test.com");

        Hotel hotel = new Hotel();
        hotel.setId(100L);
        hotel.setName("Test Hotel");

        Room room = new Room();
        room.setId(20L);
        room.setRoomNumber("101");
        room.setHotel(hotel);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUser(user);
        booking.setRoom(room);
        booking.setCheckInDate(LocalDate.of(2026, 1, 10));
        booking.setCheckOutDate(LocalDate.of(2026, 1, 12));
        booking.setStatus("PENDING");
        booking.setTotalPrice(200);

        BookingResponseDto dto = bookingMapper.toDto(booking);

        Assertions.assertNotNull(dto);

        Assertions.assertNotNull(dto.getId());
        Assertions.assertNotNull(dto.getCheckInDate());
        Assertions.assertNotNull(dto.getCheckOutDate());
        Assertions.assertNotNull(dto.getStatus());

        Assertions.assertNotNull(dto.getUserId());
        Assertions.assertNotNull(dto.getUserEmail());

        Assertions.assertNotNull(dto.getRoomId());
        Assertions.assertNotNull(dto.getRoomNumber());
        Assertions.assertNotNull(dto.getHotelName());

        Assertions.assertEquals(booking.getId(), dto.getId());
        Assertions.assertEquals(booking.getCheckInDate(), dto.getCheckInDate());
        Assertions.assertEquals(booking.getCheckOutDate(), dto.getCheckOutDate());
        Assertions.assertEquals(booking.getStatus(), dto.getStatus());
        Assertions.assertEquals(booking.getTotalPrice(), dto.getTotalPrice());

        Assertions.assertEquals(user.getId(), dto.getUserId());
        Assertions.assertEquals(user.getEmail(), dto.getUserEmail());

        Assertions.assertEquals(room.getId(), dto.getRoomId());
        Assertions.assertEquals(room.getRoomNumber(), dto.getRoomNumber());
        Assertions.assertEquals(hotel.getName(), dto.getHotelName());
    }

    @Test
    void convertDtoToEntity() {
        BookingRequestDto dto = new BookingRequestDto();
        dto.setRoomId(20L);
        dto.setCheckInDate(LocalDate.of(2026, 2, 1));
        dto.setCheckOutDate(LocalDate.of(2026, 2, 5));

        Booking booking = bookingMapper.toEntity(dto);

        Assertions.assertNotNull(booking);

        Assertions.assertNotNull(booking.getCheckInDate());
        Assertions.assertNotNull(booking.getCheckOutDate());

        Assertions.assertEquals(dto.getCheckInDate(), booking.getCheckInDate());
        Assertions.assertEquals(dto.getCheckOutDate(), booking.getCheckOutDate());

        // roomId is not mapped to Room in mapper
        Assertions.assertNull(booking.getRoom());
        Assertions.assertNull(booking.getUser());
    }

    @Test
    void convertEntityListToDtoList() {
        User user = new User();
        user.setId(10L);
        user.setEmail("user@test.com");

        Hotel hotel = new Hotel();
        hotel.setId(100L);
        hotel.setName("Test Hotel");

        Room room = new Room();
        room.setId(20L);
        room.setRoomNumber("101");
        room.setHotel(hotel);

        Booking b1 = new Booking();
        b1.setId(1L);
        b1.setUser(user);
        b1.setRoom(room);
        b1.setCheckInDate(LocalDate.of(2026, 1, 10));
        b1.setCheckOutDate(LocalDate.of(2026, 1, 12));
        b1.setStatus("PENDING");
        b1.setTotalPrice(200);

        Booking b2 = new Booking();
        b2.setId(2L);
        b2.setUser(user);
        b2.setRoom(room);
        b2.setCheckInDate(LocalDate.of(2026, 3, 1));
        b2.setCheckOutDate(LocalDate.of(2026, 3, 2));
        b2.setStatus("CONFIRMED");
        b2.setTotalPrice(100);

        List<Booking> bookings = new ArrayList<>();
        bookings.add(b1);
        bookings.add(b2);

        List<BookingResponseDto> dtoList = bookingMapper.toDtoList(bookings);

        Assertions.assertNotNull(dtoList);
        Assertions.assertNotEquals(0, dtoList.size());
        Assertions.assertEquals(bookings.size(), dtoList.size());

        for (int i = 0; i < dtoList.size(); i++) {
            BookingResponseDto dto = dtoList.get(i);
            Booking entity = bookings.get(i);

            Assertions.assertNotNull(dto);
            Assertions.assertNotNull(dto.getId());
            Assertions.assertNotNull(dto.getUserId());
            Assertions.assertNotNull(dto.getRoomId());

            Assertions.assertEquals(entity.getId(), dto.getId());
            Assertions.assertEquals(entity.getUser().getId(), dto.getUserId());
            Assertions.assertEquals(entity.getRoom().getId(), dto.getRoomId());
        }
    }
}
