package com.example.booking.Mapper;

import com.example.booking.Dto.request.RatePlanRequestDto;
import com.example.booking.Dto.response.RatePlanResponseDto;
import com.example.booking.Entity.RatePlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RatePlanMapper {

    @Mapping(source = "unit.id", target = "unitId")
    RatePlanResponseDto toDto(RatePlan ratePlan);

    @Mapping(source = "unitId", target = "unit.id")
    RatePlan toEntity(RatePlanRequestDto dto);

    List<RatePlanResponseDto> toDtoList(List<RatePlan> ratePlans);
}
