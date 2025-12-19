package com.example.booking.Mapper;

import com.example.booking.Dto.PaymentRequestDto;
import com.example.booking.Dto.PaymentResponseDto;
import com.example.booking.Entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(source = "booking.id", target = "bookingId")
    PaymentResponseDto toDto(Payment payment);

    @Mapping(source = "bookingId", target = "booking.id")
    Payment toEntity(PaymentRequestDto dto);

    List<PaymentResponseDto> toDtoList(List<Payment> payments);
}
