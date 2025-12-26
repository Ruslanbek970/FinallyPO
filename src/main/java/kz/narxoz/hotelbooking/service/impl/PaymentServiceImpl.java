package kz.narxoz.hotelbooking.service.impl;

import kz.narxoz.hotelbooking.dto.request.PaymentRequestDto;
import kz.narxoz.hotelbooking.dto.response.PaymentResponseDto;
import kz.narxoz.hotelbooking.mapper.PaymentMapper;
import kz.narxoz.hotelbooking.model.Booking;
import kz.narxoz.hotelbooking.model.Payment;
import kz.narxoz.hotelbooking.model.User;
import kz.narxoz.hotelbooking.repository.BookingRepository;
import kz.narxoz.hotelbooking.repository.PaymentRepository;
import kz.narxoz.hotelbooking.service.PaymentService;
import kz.narxoz.hotelbooking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final PaymentMapper paymentMapper;
    private final UserService userService;

    @Override
    public PaymentResponseDto pay(PaymentRequestDto dto) {

        User user = userService.getCurrentUserEntity();
        if (user == null) throw new RuntimeException("Unauthorized");

        Booking booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // ✅ нельзя платить за чужую бронь
        if (!booking.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        // ✅ нельзя платить если бронь отменена
        if ("CANCELLED".equals(booking.getStatus())) {
            throw new RuntimeException("Booking cancelled");
        }

        Payment payment = paymentRepository.findByBooking(booking).orElse(null);

        // ✅ уже оплачено
        if (payment != null && "PAID".equals(payment.getStatus())) {
            throw new RuntimeException("Already paid");
        }

        if (payment == null) payment = new Payment();

        payment.setBooking(booking);
        payment.setMethod(dto.getMethod());
        payment.setStatus("PAID");
        payment.setAmount(booking.getTotalPrice());
        payment.setPaidAt(LocalDateTime.now());

        Payment saved = paymentRepository.save(payment);

        // ✅ после оплаты: бронь становится PAID (а CONFIRMED делает менеджер)
        booking.setStatus("PAID");
        bookingRepository.save(booking);

        return paymentMapper.toDto(saved);
    }
}
