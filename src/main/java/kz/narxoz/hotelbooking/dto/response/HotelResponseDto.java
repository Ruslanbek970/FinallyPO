package kz.narxoz.hotelbooking.dto.response;

import lombok.Data;

@Data
public class HotelResponseDto {
    private Long id;
    private String name;
    private String address;
    private String description;
    private double rating;
    private boolean active;

    private Long cityId;
    private String cityName;

    private Long managerId;
    private String managerFullName;
}
