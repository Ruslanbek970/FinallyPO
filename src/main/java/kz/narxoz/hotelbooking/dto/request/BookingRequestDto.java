package kz.narxoz.hotelbooking.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequestDto {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long roomId;
}
