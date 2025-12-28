package kz.narxoz.hotelbooking.service;

import kz.narxoz.hotelbooking.dto.request.PaymentRequestDto;
import kz.narxoz.hotelbooking.dto.response.PaymentResponseDto;
import kz.narxoz.hotelbooking.mapper.PaymentMapper;
import kz.narxoz.hotelbooking.model.Booking;
import kz.narxoz.hotelbooking.model.Payment;
import kz.narxoz.hotelbooking.model.User;
import kz.narxoz.hotelbooking.repository.BookingRepository;
import kz.narxoz.hotelbooking.repository.PaymentRepository;
import kz.narxoz.hotelbooking.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    @Test
    void payUnauthorized() {
        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        BookingRepository bookingRepository = mock(BookingRepository.class);
        PaymentMapper paymentMapper = mock(PaymentMapper.class);
        UserService userService = mock(UserService.class);

        PaymentService paymentService = new PaymentServiceImpl(paymentRepository, bookingRepository, paymentMapper, userService);

        when(userService.getCurrentUserEntity()).thenReturn(null);

        PaymentRequestDto dto = new PaymentRequestDto();
        dto.setBookingId(1L);
        dto.setMethod("CARD");

        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> paymentService.pay(dto));
        Assertions.assertEquals("Unauthorized", ex.getMessage());
    }

    @Test
    void payWhenBookingNotFound() {
        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        BookingRepository bookingRepository = mock(BookingRepository.class);
        PaymentMapper paymentMapper = mock(PaymentMapper.class);
        UserService userService = mock(UserService.class);

        PaymentService paymentService = new PaymentServiceImpl(paymentRepository, bookingRepository, paymentMapper, userService);

        User user = new User();
        user.setId(1L);
        when(userService.getCurrentUserEntity()).thenReturn(user);

        PaymentRequestDto dto = new PaymentRequestDto();
        dto.setBookingId(1L);
        dto.setMethod("CARD");

        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> paymentService.pay(dto));
        Assertions.assertEquals("Booking not found", ex.getMessage());
    }

    @Test
    void payWhenAccessDenied() {
        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        BookingRepository bookingRepository = mock(BookingRepository.class);
        PaymentMapper paymentMapper = mock(PaymentMapper.class);
        UserService userService = mock(UserService.class);

        PaymentService paymentService = new PaymentServiceImpl(paymentRepository, bookingRepository, paymentMapper, userService);

        User user = new User();
        user.setId(1L);
        when(userService.getCurrentUserEntity()).thenReturn(user);

        User other = new User();
        other.setId(2L);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUser(other);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        PaymentRequestDto dto = new PaymentRequestDto();
        dto.setBookingId(1L);
        dto.setMethod("CARD");

        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> paymentService.pay(dto));
        Assertions.assertEquals("Access denied", ex.getMessage());
    }

    @Test
    void payWhenCancelled() {
        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        BookingRepository bookingRepository = mock(BookingRepository.class);
        PaymentMapper paymentMapper = mock(PaymentMapper.class);
        UserService userService = mock(UserService.class);

        PaymentService paymentService = new PaymentServiceImpl(paymentRepository, bookingRepository, paymentMapper, userService);

        User user = new User();
        user.setId(1L);
        when(userService.getCurrentUserEntity()).thenReturn(user);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUser(user);
        booking.setStatus("CANCELLED");

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        PaymentRequestDto dto = new PaymentRequestDto();
        dto.setBookingId(1L);
        dto.setMethod("CARD");

        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> paymentService.pay(dto));
        Assertions.assertEquals("Booking cancelled", ex.getMessage());
    }

    @Test
    void payWhenAlreadyPaid() {
        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        BookingRepository bookingRepository = mock(BookingRepository.class);
        PaymentMapper paymentMapper = mock(PaymentMapper.class);
        UserService userService = mock(UserService.class);

        PaymentService paymentService = new PaymentServiceImpl(paymentRepository, bookingRepository, paymentMapper, userService);

        User user = new User();
        user.setId(1L);
        when(userService.getCurrentUserEntity()).thenReturn(user);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUser(user);
        booking.setStatus("PENDING");
        booking.setTotalPrice(200);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        Payment existing = new Payment();
        existing.setStatus("PAID");

        when(paymentRepository.findByBooking(booking)).thenReturn(Optional.of(existing));

        PaymentRequestDto dto = new PaymentRequestDto();
        dto.setBookingId(1L);
        dto.setMethod("CARD");

        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> paymentService.pay(dto));
        Assertions.assertEquals("Already paid", ex.getMessage());
    }

    @Test
    void paySuccess() {
        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        BookingRepository bookingRepository = mock(BookingRepository.class);
        PaymentMapper paymentMapper = mock(PaymentMapper.class);
        UserService userService = mock(UserService.class);

        PaymentService paymentService = new PaymentServiceImpl(paymentRepository, bookingRepository, paymentMapper, userService);

        User user = new User();
        user.setId(1L);
        when(userService.getCurrentUserEntity()).thenReturn(user);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUser(user);
        booking.setStatus("PENDING");
        booking.setTotalPrice(200);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(paymentRepository.findByBooking(booking)).thenReturn(Optional.empty());

        Payment saved = new Payment();
        saved.setId(10L);
        saved.setBooking(booking);
        saved.setStatus("PAID");

        when(paymentRepository.save(any(Payment.class))).thenReturn(saved);

        PaymentResponseDto response = new PaymentResponseDto();
        response.setId(10L);

        when(paymentMapper.toDto(saved)).thenReturn(response);

        PaymentRequestDto dto = new PaymentRequestDto();
        dto.setBookingId(1L);
        dto.setMethod("CARD");

        PaymentResponseDto result = paymentService.pay(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(10L, result.getId());

        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(bookingRepository, times(1)).save(booking);
        verify(paymentMapper, times(1)).toDto(saved);
    }
}
