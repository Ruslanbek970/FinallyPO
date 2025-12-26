package kz.narxoz.hotelbooking.dto.request;

import lombok.Data;

@Data
public class ReviewRequestDto {
    private int rating;
    private String comment;
    private Long hotelId;
}
