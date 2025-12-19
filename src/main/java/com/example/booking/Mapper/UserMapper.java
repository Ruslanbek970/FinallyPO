package com.example.booking.Mapper;

import com.example.booking.Dto.response.UserResponseDto;
import com.example.booking.Dto.request.UserRequestDto;
import com.example.booking.Entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toDto(User user);

    User toEntity(UserRequestDto dto);

    List<UserResponseDto> toDtoList(List<User> users);
}
