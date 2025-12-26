package kz.narxoz.hotelbooking.service;

import kz.narxoz.hotelbooking.dto.request.AdminCreateUserRequestDto;
import kz.narxoz.hotelbooking.dto.response.UserResponseDto;

import java.util.List;

public interface AdminService {
    UserResponseDto createUser(AdminCreateUserRequestDto dto);
    Boolean blockUser(Long id);
    Boolean deleteUser(Long id);
    Boolean setRole(Long userId, String roleName);
    List<UserResponseDto> getAllUsers();
}
