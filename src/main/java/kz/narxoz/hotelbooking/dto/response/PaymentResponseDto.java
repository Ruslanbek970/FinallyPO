package kz.narxoz.hotelbooking.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentResponseDto {
    private Long id;
    private Long bookingId;
    private String method;
    private String status;
    private int amount;
    private LocalDateTime paidAt;
}
