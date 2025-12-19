package com.example.booking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.booking.Enum.PropertyType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyRequestDto {
    private PropertyType type;
    private String name;
    private String description;
    private String city;
    private String address;
    private Long ownerId;
}