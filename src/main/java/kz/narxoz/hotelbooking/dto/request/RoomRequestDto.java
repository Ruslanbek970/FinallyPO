package kz.narxoz.hotelbooking.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class RoomRequestDto {
    private String roomNumber;
    private String type;
    private int pricePerNight;
    private int capacity;
    private boolean available = true;

    private Long hotelId;

    // ids amenities
    private List<Long> amenityIds;
}
