package kz.narxoz.hotelbooking.mapper;

import kz.narxoz.hotelbooking.dto.request.PaymentRequestDto;
import kz.narxoz.hotelbooking.dto.response.PaymentResponseDto;
import kz.narxoz.hotelbooking.model.Booking;
import kz.narxoz.hotelbooking.model.Payment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaymentMapperTest {

    private final PaymentMapper paymentMapper = Mappers.getMapper(PaymentMapper.class);

    @Test
    void convertEntityToDto() {
        Booking booking = new Booking();
        booking.setId(10L);

        Payment payment = new Payment();
        payment.setId(1L);
        payment.setBooking(booking);
        payment.setMethod("CARD");
        payment.setStatus("PAID");
        payment.setAmount(500);
        payment.setPaidAt(LocalDateTime.of(2026, 1, 1, 12, 0));

        PaymentResponseDto dto = paymentMapper.toDto(payment);

        Assertions.assertNotNull(dto);

        Assertions.assertNotNull(dto.getId());
        Assertions.assertNotNull(dto.getBookingId());
        Assertions.assertNotNull(dto.getMethod());
        Assertions.assertNotNull(dto.getStatus());
        Assertions.assertNotNull(dto.getPaidAt());

        Assertions.assertEquals(payment.getId(), dto.getId());
        Assertions.assertEquals(booking.getId(), dto.getBookingId());
        Assertions.assertEquals(payment.getMethod(), dto.getMethod());
        Assertions.assertEquals(payment.getStatus(), dto.getStatus());
        Assertions.assertEquals(payment.getAmount(), dto.getAmount());
        Assertions.assertEquals(payment.getPaidAt(), dto.getPaidAt());
    }

    @Test
    void convertDtoToEntity() {
        PaymentRequestDto dto = new PaymentRequestDto();
        dto.setBookingId(10L);
        dto.setMethod("CARD");

        Payment payment = paymentMapper.toEntity(dto);

        Assertions.assertNotNull(payment);
        Assertions.assertNotNull(payment.getMethod());
        Assertions.assertEquals(dto.getMethod(), payment.getMethod());

        // bookingId is not mapped to Booking in mapper
        Assertions.assertNull(payment.getBooking());
    }

    @Test
    void convertEntityListToDtoList() {
        Booking booking = new Booking();
        booking.setId(10L);

        Payment p1 = new Payment();
        p1.setId(1L);
        p1.setBooking(booking);
        p1.setMethod("CARD");
        p1.setStatus("PAID");
        p1.setAmount(500);
        p1.setPaidAt(LocalDateTime.of(2026, 1, 1, 12, 0));

        Payment p2 = new Payment();
        p2.setId(2L);
        p2.setBooking(booking);
        p2.setMethod("CASH");
        p2.setStatus("PAID");
        p2.setAmount(200);
        p2.setPaidAt(LocalDateTime.of(2026, 1, 2, 12, 0));

        List<Payment> payments = new ArrayList<>();
        payments.add(p1);
        payments.add(p2);

        List<PaymentResponseDto> dtoList = paymentMapper.toDtoList(payments);

        Assertions.assertNotNull(dtoList);
        Assertions.assertNotEquals(0, dtoList.size());
        Assertions.assertEquals(payments.size(), dtoList.size());

        for (int i = 0; i < dtoList.size(); i++) {
            PaymentResponseDto dto = dtoList.get(i);
            Payment entity = payments.get(i);

            Assertions.assertNotNull(dto);
            Assertions.assertNotNull(dto.getId());
            Assertions.assertNotNull(dto.getBookingId());

            Assertions.assertEquals(entity.getId(), dto.getId());
            Assertions.assertEquals(entity.getBooking().getId(), dto.getBookingId());
        }
    }
}
