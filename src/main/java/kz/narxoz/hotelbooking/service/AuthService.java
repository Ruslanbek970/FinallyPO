package kz.narxoz.hotelbooking.service;

import jakarta.servlet.http.HttpServletRequest;
import kz.narxoz.hotelbooking.dto.request.LoginRequestDto;
import kz.narxoz.hotelbooking.dto.response.LoginResponseDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto dto, HttpServletRequest request);
}
