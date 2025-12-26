package kz.narxoz.hotelbooking.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponseDto {
    private Long id;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;

    private Long userId;
    private String userEmail;

    private Long hotelId;
    private String hotelName;
}
