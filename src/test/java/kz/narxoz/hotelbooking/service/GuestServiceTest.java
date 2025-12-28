package kz.narxoz.hotelbooking.service;

import kz.narxoz.hotelbooking.dto.request.GuestRequestDto;
import kz.narxoz.hotelbooking.dto.response.GuestResponseDto;
import kz.narxoz.hotelbooking.mapper.GuestMapper;
import kz.narxoz.hotelbooking.model.Booking;
import kz.narxoz.hotelbooking.model.Guest;
import kz.narxoz.hotelbooking.repository.BookingRepository;
import kz.narxoz.hotelbooking.repository.GuestRepository;
import kz.narxoz.hotelbooking.service.impl.GuestServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class GuestServiceTest {

    @Test
    void createWhenBookingNotFound() {
        GuestRepository guestRepository = mock(GuestRepository.class);
        BookingRepository bookingRepository = mock(BookingRepository.class);
        GuestMapper guestMapper = mock(GuestMapper.class);

        GuestService guestService = new GuestServiceImpl(guestRepository, bookingRepository, guestMapper);

        GuestRequestDto dto = new GuestRequestDto();
        dto.setBookingId(1L);

        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());

        GuestResponseDto result = guestService.create(dto);

        Assertions.assertNull(result);

        verify(bookingRepository, times(1)).findById(1L);
        verify(guestRepository, never()).save(any());
    }

    @Test
    void createSuccess() {
        GuestRepository guestRepository = mock(GuestRepository.class);
        BookingRepository bookingRepository = mock(BookingRepository.class);
        GuestMapper guestMapper = mock(GuestMapper.class);

        GuestService guestService = new GuestServiceImpl(guestRepository, bookingRepository, guestMapper);

        Booking booking = new Booking();
        booking.setId(1L);

        GuestRequestDto dto = new GuestRequestDto();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setDocumentNumber("KZ123");
        dto.setBookingId(1L);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        Guest saved = new Guest();
        saved.setId(10L);
        saved.setBooking(booking);

        when(guestRepository.save(any(Guest.class))).thenReturn(saved);

        GuestResponseDto response = new GuestResponseDto();
        response.setId(10L);
        response.setBookingId(1L);

        when(guestMapper.toDto(saved)).thenReturn(response);

        GuestResponseDto result = guestService.create(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(10L, result.getId());
        Assertions.assertEquals(1L, result.getBookingId());

        verify(bookingRepository, times(1)).findById(1L);
        verify(guestRepository, times(1)).save(any(Guest.class));
        verify(guestMapper, times(1)).toDto(saved);
    }

    @Test
    void getByBookingWhenBookingNotFound() {
        GuestRepository guestRepository = mock(GuestRepository.class);
        BookingRepository bookingRepository = mock(BookingRepository.class);
        GuestMapper guestMapper = mock(GuestMapper.class);

        GuestService guestService = new GuestServiceImpl(guestRepository, bookingRepository, guestMapper);

        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());

        List<GuestResponseDto> result = guestService.getByBooking(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());

        verify(bookingRepository, times(1)).findById(1L);
        verify(guestRepository, never()).findAllByBooking(any());
    }
}
