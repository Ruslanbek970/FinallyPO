package com.example.booking.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmenityResponseDto {
    private Long id;
    private String code;
    private String title;
}
