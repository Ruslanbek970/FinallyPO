package kz.narxoz.hotelbooking.service.impl;

import kz.narxoz.hotelbooking.dto.request.PaymentRequestDto;
import kz.narxoz.hotelbooking.dto.response.PaymentResponseDto;
import kz.narxoz.hotelbooking.mapper.PaymentMapper;
import kz.narxoz.hotelbooking.model.Booking;
import kz.narxoz.hotelbooking.model.Payment;
import kz.narxoz.hotelbooking.model.User;
import kz.narxoz.hotelbooking.repository.BookingRepository;
import kz.narxoz.hotelbooking.repository.PaymentRepository;
import kz.narxoz.hotelbooking.service.UserService;
import kz.narxoz.hotelbooking.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    private PaymentRepository paymentRepository;
    private BookingRepository bookingRepository;
    private PaymentMapper paymentMapper;
    private UserService userService;

    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        paymentRepository = mock(PaymentRepository.class);
        bookingRepository = mock(BookingRepository.class);
        paymentMapper = mock(PaymentMapper.class);
        userService = mock(UserService.class);

        paymentService = new PaymentServiceImpl(paymentRepository, bookingRepository, paymentMapper, userService);
    }

    @Test
    void pay_shouldThrowIfUnauthorized() {
        when(userService.getCurrentUserEntity()).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> paymentService.pay(new PaymentRequestDto()));
        assertEquals("Unauthorized", ex.getMessage());
    }

    @Test
    void pay_shouldThrowIfBookingNotFound() {
        User user = new User();
        user.setId(1L);

        PaymentRequestDto dto = new PaymentRequestDto();
        dto.setBookingId(10L);

        when(userService.getCurrentUserEntity()).thenReturn(user);
        when(bookingRepository.findById(10L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> paymentService.pay(dto));
        assertEquals("Booking not found", ex.getMessage());
    }

    @Test
    void pay_shouldThrowIfAccessDenied() {
        User user = new User();
        user.setId(1L);

        User other = new User();
        other.setId(2L);

        Booking booking = new Booking();
        booking.setId(10L);
        booking.setUser(other);

        PaymentRequestDto dto = new PaymentRequestDto();
        dto.setBookingId(10L);

        when(userService.getCurrentUserEntity()).thenReturn(user);
        when(bookingRepository.findById(10L)).thenReturn(Optional.of(booking));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> paymentService.pay(dto));
        assertEquals("Access denied", ex.getMessage());
    }

    @Test
    void pay_shouldThrowIfBookingCancelled() {
        User user = new User();
        user.setId(1L);

        Booking booking = new Booking();
        booking.setId(10L);
        booking.setUser(user);
        booking.setStatus("CANCELLED");

        PaymentRequestDto dto = new PaymentRequestDto();
        dto.setBookingId(10L);

        when(userService.getCurrentUserEntity()).thenReturn(user);
        when(bookingRepository.findById(10L)).thenReturn(Optional.of(booking));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> paymentService.pay(dto));
        assertEquals("Booking cancelled", ex.getMessage());
    }

    @Test
    void pay_shouldThrowIfAlreadyPaid() {
        User user = new User();
        user.setId(1L);

        Booking booking = new Booking();
        booking.setId(10L);
        booking.setUser(user);
        booking.setStatus("PENDING");
        booking.setTotalPrice(70000);

        Payment existing = new Payment();
        existing.setId(5L);
        existing.setBooking(booking);
        existing.setStatus("PAID");

        PaymentRequestDto dto = new PaymentRequestDto();
        dto.setBookingId(10L);
        dto.setMethod("CARD");

        when(userService.getCurrentUserEntity()).thenReturn(user);
        when(bookingRepository.findById(10L)).thenReturn(Optional.of(booking));
        when(paymentRepository.findByBooking(booking)).thenReturn(Optional.of(existing));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> paymentService.pay(dto));
        assertEquals("Already paid", ex.getMessage());
    }

    @Test
    void pay_shouldCreatePayment_andSetBookingPaid() {
        User user = new User();
        user.setId(1L);

        Booking booking = new Booking();
        booking.setId(10L);
        booking.setUser(user);
        booking.setStatus("PENDING");
        booking.setTotalPrice(50000);

        Payment saved = new Payment();
        saved.setId(5L);
        saved.setBooking(booking);
        saved.setStatus("PAID");

        PaymentResponseDto responseDto = new PaymentResponseDto();
        responseDto.setId(5L);
        responseDto.setBookingId(10L);
        responseDto.setStatus("PAID");

        PaymentRequestDto dto = new PaymentRequestDto();
        dto.setBookingId(10L);
        dto.setMethod("CARD");

        when(userService.getCurrentUserEntity()).thenReturn(user);
        when(bookingRepository.findById(10L)).thenReturn(Optional.of(booking));
        when(paymentRepository.findByBooking(booking)).thenReturn(Optional.empty());
        when(paymentRepository.save(any(Payment.class))).thenReturn(saved);
        when(paymentMapper.toDto(saved)).thenReturn(responseDto);

        PaymentResponseDto res = paymentService.pay(dto);

        assertNotNull(res);
        assertEquals("PAID", res.getStatus());
        assertEquals("PAID", booking.getStatus());

        verify(bookingRepository).save(booking);
        verify(paymentRepository).save(any(Payment.class));
    }
}
