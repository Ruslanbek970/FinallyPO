package kz.narxoz.hotelbooking.service.impl;

import kz.narxoz.hotelbooking.dto.request.GuestRequestDto;
import kz.narxoz.hotelbooking.dto.response.GuestResponseDto;
import kz.narxoz.hotelbooking.mapper.GuestMapper;
import kz.narxoz.hotelbooking.model.Booking;
import kz.narxoz.hotelbooking.model.Guest;
import kz.narxoz.hotelbooking.repository.BookingRepository;
import kz.narxoz.hotelbooking.repository.GuestRepository;
import kz.narxoz.hotelbooking.service.impl.GuestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GuestServiceImplTest {

    private GuestRepository guestRepository;
    private BookingRepository bookingRepository;
    private GuestMapper guestMapper;

    private GuestServiceImpl guestService;

    @BeforeEach
    void setUp() {
        guestRepository = mock(GuestRepository.class);
        bookingRepository = mock(BookingRepository.class);
        guestMapper = mock(GuestMapper.class);

        guestService = new GuestServiceImpl(guestRepository, bookingRepository, guestMapper);
    }

    @Test
    void create_shouldReturnNullIfBookingNotFound() {
        GuestRequestDto dto = new GuestRequestDto();
        dto.setBookingId(10L);

        when(bookingRepository.findById(10L)).thenReturn(Optional.empty());

        assertNull(guestService.create(dto));
        verify(guestRepository, never()).save(any());
    }

    @Test
    void create_shouldCreateGuest() {
        Booking booking = new Booking();
        booking.setId(10L);

        GuestRequestDto dto = new GuestRequestDto();
        dto.setBookingId(10L);
        dto.setFirstName("A");
        dto.setLastName("B");
        dto.setDocumentNumber("ID123");

        when(bookingRepository.findById(10L)).thenReturn(Optional.of(booking));

        Guest saved = new Guest();
        saved.setId(1L);

        GuestResponseDto responseDto = new GuestResponseDto();
        responseDto.setId(1L);

        when(guestRepository.save(any(Guest.class))).thenReturn(saved);
        when(guestMapper.toDto(saved)).thenReturn(responseDto);

        GuestResponseDto res = guestService.create(dto);

        assertNotNull(res);
        assertEquals(1L, res.getId());
        verify(guestRepository).save(any(Guest.class));
    }

    @Test
    void getByBooking_shouldReturnEmptyIfBookingNotFound() {
        when(bookingRepository.findById(99L)).thenReturn(Optional.empty());

        assertTrue(guestService.getByBooking(99L).isEmpty());
    }

    @Test
    void getByBooking_shouldReturnGuests() {
        Booking booking = new Booking();
        booking.setId(10L);

        when(bookingRepository.findById(10L)).thenReturn(Optional.of(booking));
        when(guestRepository.findAllByBooking(booking)).thenReturn(List.of(new Guest()));
        when(guestMapper.toDtoList(anyList())).thenReturn(List.of(new GuestResponseDto()));

        List<GuestResponseDto> res = guestService.getByBooking(10L);

        assertEquals(1, res.size());
        verify(guestRepository).findAllByBooking(booking);
    }
}
