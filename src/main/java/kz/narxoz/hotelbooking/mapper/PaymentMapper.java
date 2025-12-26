package kz.narxoz.hotelbooking.mapper;

import kz.narxoz.hotelbooking.dto.request.PaymentRequestDto;
import kz.narxoz.hotelbooking.dto.response.PaymentResponseDto;
import kz.narxoz.hotelbooking.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(source = "booking.id", target = "bookingId")
    PaymentResponseDto toDto(Payment payment);

    List<PaymentResponseDto> toDtoList(List<Payment> payments);

    Payment toEntity(PaymentRequestDto dto);
}
