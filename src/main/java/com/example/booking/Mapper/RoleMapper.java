package com.example.booking.Mapper;

import com.example.booking.Dto.request.RoleRequestDto;
import com.example.booking.Dto.response.RoleResponseDto;
import com.example.booking.Entity.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleResponseDto toDto(Role role);

    Role toEntity(RoleRequestDto dto);

    List<RoleResponseDto> toDtoList(List<Role> roles);
}
