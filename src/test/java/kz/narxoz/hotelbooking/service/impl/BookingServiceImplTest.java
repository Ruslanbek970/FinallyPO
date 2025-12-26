package kz.narxoz.hotelbooking.service.impl;

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
import kz.narxoz.hotelbooking.service.UserService;
import kz.narxoz.hotelbooking.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceImplTest {

    private BookingRepository bookingRepository;
    private RoomRepository roomRepository;
    private HotelRepository hotelRepository;
    private UserService userService;
    private BookingMapper bookingMapper;

    private BookingServiceImpl bookingService;

    @BeforeEach
    void setUp() {
        bookingRepository = mock(BookingRepository.class);
        roomRepository = mock(RoomRepository.class);
        hotelRepository = mock(HotelRepository.class);
        userService = mock(UserService.class);
        bookingMapper = mock(BookingMapper.class);

        bookingService = new BookingServiceImpl(bookingRepository, roomRepository, hotelRepository, userService, bookingMapper);
    }

    @Test
    void create_shouldThrowIfNotAuth() {
        when(userService.getCurrentUserEntity()).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> bookingService.create(new BookingRequestDto()));
        assertEquals("Unauthorized", ex.getMessage());
    }

    @Test
    void create_shouldThrowIfRoomNotFound() {
        User u = new User();
        u.setId(1L);

        BookingRequestDto dto = new BookingRequestDto();
        dto.setRoomId(10L);
        dto.setCheckInDate(LocalDate.now().plusDays(1));
        dto.setCheckOutDate(LocalDate.now().plusDays(2));

        when(userService.getCurrentUserEntity()).thenReturn(u);
        when(roomRepository.findById(10L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> bookingService.create(dto));
        assertEquals("Room not found", ex.getMessage());
    }

    @Test
    void create_shouldThrowIfRoomNotAvailable() {
        User u = new User();
        u.setId(1L);

        Room room = new Room();
        room.setId(10L);
        room.setAvailable(false);

        BookingRequestDto dto = new BookingRequestDto();
        dto.setRoomId(10L);
        dto.setCheckInDate(LocalDate.now().plusDays(1));
        dto.setCheckOutDate(LocalDate.now().plusDays(2));

        when(userService.getCurrentUserEntity()).thenReturn(u);
        when(roomRepository.findById(10L)).thenReturn(Optional.of(room));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> bookingService.create(dto));
        assertEquals("Room is not available", ex.getMessage());
    }

    @Test
    void create_shouldCreateBooking_andMakeRoomUnavailable() {
        User u = new User();
        u.setId(1L);

        Room room = new Room();
        room.setId(10L);
        room.setAvailable(true);
        room.setPricePerNight(10000);

        BookingRequestDto dto = new BookingRequestDto();
        dto.setRoomId(10L);
        dto.setCheckInDate(LocalDate.now().plusDays(1));
        dto.setCheckOutDate(LocalDate.now().plusDays(3)); // 2 nights

        Booking saved = new Booking();
        saved.setId(5L);

        BookingResponseDto responseDto = new BookingResponseDto();
        responseDto.setId(5L);

        when(userService.getCurrentUserEntity()).thenReturn(u);
        when(roomRepository.findById(10L)).thenReturn(Optional.of(room));
        when(bookingRepository.save(any(Booking.class))).thenReturn(saved);
        when(bookingMapper.toDto(saved)).thenReturn(responseDto);

        BookingResponseDto res = bookingService.create(dto);

        assertNotNull(res);
        assertEquals(5L, res.getId());

        assertFalse(room.isAvailable());
        verify(roomRepository).save(room);
        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    void cancel_shouldThrowIfAccessDenied() {
        User user = new User();
        user.setId(1L);

        User other = new User();
        other.setId(2L);

        Room room = new Room();
        room.setId(10L);

        Booking booking = new Booking();
        booking.setId(5L);
        booking.setUser(other); // чужая бронь
        booking.setRoom(room);

        when(userService.getCurrentUserEntity()).thenReturn(user);
        when(bookingRepository.findById(5L)).thenReturn(Optional.of(booking));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> bookingService.cancel(5L));
        assertEquals("Access denied", ex.getMessage());
    }

    @Test
    void confirmByManager_shouldThrowIfAccessDenied() {
        User manager = new User();
        manager.setId(1L);

        User otherManager = new User();
        otherManager.setId(2L);

        Hotel hotel = new Hotel();
        hotel.setId(100L);
        hotel.setManager(otherManager);

        Room room = new Room();
        room.setId(10L);
        room.setHotel(hotel);

        Booking booking = new Booking();
        booking.setId(5L);
        booking.setRoom(room);

        when(userService.getCurrentUserEntity()).thenReturn(manager);
        when(bookingRepository.findById(5L)).thenReturn(Optional.of(booking));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> bookingService.confirmByManager(5L));
        assertEquals("Access denied", ex.getMessage());
    }
}
