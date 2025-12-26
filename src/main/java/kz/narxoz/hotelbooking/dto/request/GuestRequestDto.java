package kz.narxoz.hotelbooking.dto.request;

import lombok.Data;

@Data
public class GuestRequestDto {
    private String firstName;
    private String lastName;
    private String documentNumber;
    private Long bookingId;
}
