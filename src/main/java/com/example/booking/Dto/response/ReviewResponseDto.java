package com.example.booking.Dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.booking.Enum.ReviewStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {
    private Long id;
    private Long bookingId;
    private Long authorId;
    private int rating;
    private String comment;
    private ReviewStatus status;
}