package kz.narxoz.hotelbooking.service;

import kz.narxoz.hotelbooking.dto.request.ChangePasswordRequestDto;
import kz.narxoz.hotelbooking.dto.request.RegisterRequestDto;
import kz.narxoz.hotelbooking.dto.request.UpdateProfileRequestDto;
import kz.narxoz.hotelbooking.dto.response.UserResponseDto;
import kz.narxoz.hotelbooking.model.User;

public interface UserService {

    UserResponseDto register(RegisterRequestDto dto);

    UserResponseDto me();

    UserResponseDto updateProfile(UpdateProfileRequestDto dto);

    void changePassword(ChangePasswordRequestDto dto);

    User getCurrentUserEntity();
}
