package kz.narxoz.hotelbooking.dto.response;

import lombok.Data;

@Data
public class GuestResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String documentNumber;
    private Long bookingId;
}
