package com.example.booking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyResponseDto {
    private Long id;
    private String type;
    private String name;
    private String description;
    private String city;
    private String address;
    private Long ownerId;
}
