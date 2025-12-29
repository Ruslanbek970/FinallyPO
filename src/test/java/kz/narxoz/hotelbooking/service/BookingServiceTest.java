package kz.narxoz.hotelbooking.service;

import kz.narxoz.hotelbooking.dto.request.BookingRequestDto;
import kz.narxoz.hotelbooking.dto.response.BookingResponseDto;
import kz.narxoz.hotelbooking.mapper.BookingMapper;
import kz.narxoz.hotelbooking.model.Booking;
import kz.narxoz.hotelbooking.model.Room;
import kz.narxoz.hotelbooking.model.User;
import kz.narxoz.hotelbooking.repository.BookingRepository;
import kz.narxoz.hotelbooking.repository.RoomRepository;
import kz.narxoz.hotelbooking.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class BookingServiceTest {

    @Test
    void createSuccess() {
        BookingRepository bookingRepository = mock(BookingRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        UserService userService = mock(UserService.class);
        BookingMapper bookingMapper = mock(BookingMapper.class);

        BookingService bookingService =
                new BookingServiceImpl(bookingRepository, roomRepository, userService, bookingMapper);


        User user = new User();
        user.setId(1L);
        when(userService.getCurrentUserEntity()).thenReturn(user);


        Room room = new Room();
        room.setId(5L);
        room.setAvailable(true);
        when(roomRepository.findById(5L)).thenReturn(Optional.of(room));


        Booking savedBooking = new Booking();
        savedBooking.setId(100L);
        when(bookingRepository.save(any(Booking.class))).thenReturn(savedBooking);


        BookingResponseDto dtoResp = new BookingResponseDto();
        dtoResp.setId(100L);
        when(bookingMapper.toDto(savedBooking)).thenReturn(dtoResp);


        BookingRequestDto dto = new BookingRequestDto();
        dto.setRoomId(5L);
        dto.setCheckInDate(LocalDate.of(2026, 1, 10));
        dto.setCheckOutDate(LocalDate.of(2026, 1, 12));

        BookingResponseDto result = bookingService.create(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(100L, result.getId());
        Assertions.assertFalse(room.isAvailable()); // логика: room занята

        verify(bookingRepository, times(1)).save(any(Booking.class));
        verify(roomRepository, times(1)).save(room);
    }


    @Test
    void getMyBookingsSuccess() {
        BookingRepository bookingRepository = mock(BookingRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        UserService userService = mock(UserService.class);
        BookingMapper bookingMapper = mock(BookingMapper.class);

        BookingService bookingService =
                new BookingServiceImpl(bookingRepository, roomRepository, userService, bookingMapper);

        User user = new User();
        user.setId(1L);
        when(userService.getCurrentUserEntity()).thenReturn(user);

        List<Booking> bookings = List.of(new Booking(), new Booking());
        when(bookingRepository.findAllByUser(user)).thenReturn(bookings);

        List<BookingResponseDto> dtoList = List.of(new BookingResponseDto(), new BookingResponseDto());
        when(bookingMapper.toDtoList(bookings)).thenReturn(dtoList);

        List<BookingResponseDto> result = bookingService.getMyBookings();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());

        verify(bookingRepository, times(1)).findAllByUser(user);
        verify(bookingMapper, times(1)).toDtoList(bookings);
    }


    @Test
    void cancelWhenDifferentUser() {
        BookingRepository bookingRepository = mock(BookingRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        UserService userService = mock(UserService.class);
        BookingMapper bookingMapper = mock(BookingMapper.class);

        BookingService bookingService =
                new BookingServiceImpl(bookingRepository, roomRepository, userService, bookingMapper);

        User current = new User();
        current.setId(1L);
        when(userService.getCurrentUserEntity()).thenReturn(current);

        User another = new User();
        another.setId(2L);

        Room room = new Room();
        room.setId(5L);

        Booking booking = new Booking();
        booking.setId(10L);
        booking.setUser(another);
        booking.setRoom(room);

        when(bookingRepository.findById(10L)).thenReturn(Optional.of(booking));

        Boolean result = bookingService.cancel(10L);

        Assertions.assertFalse(result);

        verify(bookingRepository, times(1)).findById(10L);
        verify(bookingRepository, never()).save(any());
        verify(roomRepository, never()).save(any());
    }
}
