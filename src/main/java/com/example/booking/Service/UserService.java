package com.example.booking.Service;

import com.example.booking.Dto.request.UserRequestDto;
import com.example.booking.Dto.response.UserResponseDto;
import com.example.booking.Enum.UserStatus;

import java.util.List;
import java.util.Set;

public interface UserService {

    // регистрация (обычно USER по умолчанию)
    UserResponseDto register(UserRequestDto dto);

    // создание пользователя админом (можно сразу roleIds)
    UserResponseDto createByAdmin(UserRequestDto dto, Set<Long> roleIds);

    UserResponseDto updateProfile(Long userId, UserRequestDto dto);

    void changePassword(Long userId, String newPassword);

    UserResponseDto changeStatus(Long userId, UserStatus status); // ACTIVE/BLOCKED

    UserResponseDto updateRoles(Long userId, Set<Long> roleIds);

    UserResponseDto getById(Long id);

    List<UserResponseDto> getAll();

    void deleteById(Long id);
}
