package kz.narxoz.hotelbooking.dto.request;

import lombok.Data;

@Data
public class PaymentRequestDto {
    private Long bookingId;
    private String method;
}
