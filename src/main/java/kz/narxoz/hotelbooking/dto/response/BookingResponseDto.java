package kz.narxoz.hotelbooking.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingResponseDto {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status;
    private int totalPrice;

    private Long userId;
    private String userEmail;

    private Long roomId;
    private String roomNumber;

    private String hotelName;
}
