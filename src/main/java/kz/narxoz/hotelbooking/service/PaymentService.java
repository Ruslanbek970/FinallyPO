package kz.narxoz.hotelbooking.service;

import kz.narxoz.hotelbooking.dto.request.PaymentRequestDto;
import kz.narxoz.hotelbooking.dto.response.PaymentResponseDto;

public interface PaymentService {
    PaymentResponseDto pay(PaymentRequestDto dto);
}
