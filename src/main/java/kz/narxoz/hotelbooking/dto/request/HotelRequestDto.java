package kz.narxoz.hotelbooking.dto.request;

import lombok.Data;

@Data
public class HotelRequestDto {
    private String name;
    private String address;
    private String description;
    private boolean active = true;
    private Long cityId;
    private Long managerId; // optional
}
