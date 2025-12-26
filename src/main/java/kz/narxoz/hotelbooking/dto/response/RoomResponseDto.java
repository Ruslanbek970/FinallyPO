package kz.narxoz.hotelbooking.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class RoomResponseDto {
    private Long id;
    private String roomNumber;
    private String type;
    private int pricePerNight;
    private int capacity;
    private boolean available;

    private Long hotelId;
    private String hotelName;

    private List<String> amenities; // names
}
