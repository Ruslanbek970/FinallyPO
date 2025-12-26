package com.example.booking.serviceImpl;

import com.example.booking.Dto.request.PaymentRequestDto;
import com.example.booking.Dto.response.PaymentResponseDto;
import com.example.booking.Entity.Booking;
import com.example.booking.Entity.Payment;
import com.example.booking.Enum.PaymentStatus;
import com.example.booking.Mapper.PaymentMapper;
import com.example.booking.Service.PaymentService;
import com.example.booking.repository.BookingRepository;
import com.example.booking.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentResponseDto create(PaymentRequestDto dto) {
        Booking booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));

        Payment payment = paymentMapper.toEntity(dto);
        payment.setBooking(booking);

        return paymentMapper.toDto(paymentRepository.save(payment));
    }

    @Override
    public PaymentResponseDto getById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));
        return paymentMapper.toDto(payment);
    }

    @Override
    public List<PaymentResponseDto> getAll() {
        return paymentMapper.toDtoList(paymentRepository.findAll());
    }

    @Override
    public List<PaymentResponseDto> getByBookingId(Long bookingId) {
        return paymentMapper.toDtoList(paymentRepository.findByBookingId(bookingId));
    }

    @Override
    public PaymentResponseDto changeStatus(Long paymentId, PaymentStatus status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));
        payment.setStatus(status);
        return paymentMapper.toDto(paymentRepository.save(payment));
    }

    @Override
    public void deleteById(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found");
        }
        paymentRepository.deleteById(id);
    }
}
