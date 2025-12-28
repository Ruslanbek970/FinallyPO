package kz.narxoz.hotelbooking.service;

import kz.narxoz.hotelbooking.dto.request.BookingRequestDto;
import kz.narxoz.hotelbooking.dto.response.BookingResponseDto;
import kz.narxoz.hotelbooking.mapper.BookingMapper;
import kz.narxoz.hotelbooking.model.Booking;
import kz.narxoz.hotelbooking.model.Hotel;
import kz.narxoz.hotelbooking.model.Room;
import kz.narxoz.hotelbooking.model.User;
import kz.narxoz.hotelbooking.repository.BookingRepository;
import kz.narxoz.hotelbooking.repository.HotelRepository;
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
    void createWhenUserNull() {
        BookingRepository bookingRepository = mock(BookingRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        HotelRepository hotelRepository = mock(HotelRepository.class);
        UserService userService = mock(UserService.class);
        BookingMapper bookingMapper = mock(BookingMapper.class);

        BookingService bookingService = new BookingServiceImpl(bookingRepository, roomRepository, hotelRepository, userService, bookingMapper);

        when(userService.getCurrentUserEntity()).thenReturn(null);

        BookingRequestDto dto = new BookingRequestDto();
        dto.setRoomId(1L);
        dto.setCheckInDate(LocalDate.of(2026, 1, 10));
        dto.setCheckOutDate(LocalDate.of(2026, 1, 11));

        BookingResponseDto result = bookingService.create(dto);

        Assertions.assertNull(result);

        verify(userService, times(1)).getCurrentUserEntity();
        verify(roomRepository, never()).findById(anyLong());
    }

    @Test
    void createSuccess() {
        BookingRepository bookingRepository = mock(BookingRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        HotelRepository hotelRepository = mock(HotelRepository.class);
        UserService userService = mock(UserService.class);
        BookingMapper bookingMapper = mock(BookingMapper.class);

        BookingService bookingService = new BookingServiceImpl(bookingRepository, roomRepository, hotelRepository, userService, bookingMapper);

        User user = new User();
        user.setId(1L);
        when(userService.getCurrentUserEntity()).thenReturn(user);

        Hotel hotel = new Hotel();
        hotel.setId(10L);

        Room room = new Room();
        room.setId(5L);
        room.setHotel(hotel);
        room.setPricePerNight(100);
        room.setAvailable(true);

        when(roomRepository.findById(5L)).thenReturn(Optional.of(room));

        Booking saved = new Booking();
        saved.setId(100L);

        when(bookingRepository.save(any(Booking.class))).thenReturn(saved);

        BookingResponseDto response = new BookingResponseDto();
        response.setId(100L);

        when(bookingMapper.toDto(saved)).thenReturn(response);

        BookingRequestDto dto = new BookingRequestDto();
        dto.setRoomId(5L);
        dto.setCheckInDate(LocalDate.of(2026, 1, 10));
        dto.setCheckOutDate(LocalDate.of(2026, 1, 12));

        BookingResponseDto result = bookingService.create(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(100L, result.getId());

        verify(userService, times(1)).getCurrentUserEntity();
        verify(roomRepository, times(1)).findById(5L);
        verify(bookingRepository, times(1)).save(any(Booking.class));
        verify(roomRepository, times(1)).save(room);
        verify(bookingMapper, times(1)).toDto(saved);
    }

    @Test
    void cancelWhenDifferentUser() {
        BookingRepository bookingRepository = mock(BookingRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        HotelRepository hotelRepository = mock(HotelRepository.class);
        UserService userService = mock(UserService.class);
        BookingMapper bookingMapper = mock(BookingMapper.class);

        BookingService bookingService = new BookingServiceImpl(bookingRepository, roomRepository, hotelRepository, userService, bookingMapper);

        User user = new User();
        user.setId(1L);
        when(userService.getCurrentUserEntity()).thenReturn(user);

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

    @Test
    void cancelSuccess() {
        BookingRepository bookingRepository = mock(BookingRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        HotelRepository hotelRepository = mock(HotelRepository.class);
        UserService userService = mock(UserService.class);
        BookingMapper bookingMapper = mock(BookingMapper.class);

        BookingService bookingService = new BookingServiceImpl(bookingRepository, roomRepository, hotelRepository, userService, bookingMapper);

        User user = new User();
        user.setId(1L);
        when(userService.getCurrentUserEntity()).thenReturn(user);

        Room room = new Room();
        room.setId(5L);
        room.setAvailable(false);

        Booking booking = new Booking();
        booking.setId(10L);
        booking.setUser(user);
        booking.setRoom(room);

        when(bookingRepository.findById(10L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        when(roomRepository.save(room)).thenReturn(room);

        Boolean result = bookingService.cancel(10L);

        Assertions.assertTrue(result);

        verify(bookingRepository, times(1)).save(booking);
        verify(roomRepository, times(1)).save(room);
    }

    @Test
    void getMyBookingsWhenUserNull() {
        BookingRepository bookingRepository = mock(BookingRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        HotelRepository hotelRepository = mock(HotelRepository.class);
        UserService userService = mock(UserService.class);
        BookingMapper bookingMapper = mock(BookingMapper.class);

        BookingService bookingService = new BookingServiceImpl(bookingRepository, roomRepository, hotelRepository, userService, bookingMapper);

        when(userService.getCurrentUserEntity()).thenReturn(null);

        List<BookingResponseDto> result = bookingService.getMyBookings();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());

        verify(userService, times(1)).getCurrentUserEntity();
        verify(bookingRepository, never()).findAllByUser(any());
    }

    @Test
    void getManagerBookingsWhenNoHotels() {
        BookingRepository bookingRepository = mock(BookingRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        HotelRepository hotelRepository = mock(HotelRepository.class);
        UserService userService = mock(UserService.class);
        BookingMapper bookingMapper = mock(BookingMapper.class);

        BookingService bookingService = new BookingServiceImpl(bookingRepository, roomRepository, hotelRepository, userService, bookingMapper);

        User manager = new User();
        manager.setId(1L);

        when(userService.getCurrentUserEntity()).thenReturn(manager);
        when(hotelRepository.findAllByManager(manager)).thenReturn(List.of());

        List<BookingResponseDto> result = bookingService.getManagerBookings();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());

        verify(hotelRepository, times(1)).findAllByManager(manager);
        verify(bookingRepository, never()).findAll();
    }
}
