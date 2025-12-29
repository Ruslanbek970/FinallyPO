package kz.narxoz.hotelbooking.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kz.narxoz.hotelbooking.dto.request.LoginRequestDto;
import kz.narxoz.hotelbooking.dto.response.LoginResponseDto;
import kz.narxoz.hotelbooking.mapper.UserMapper;
import kz.narxoz.hotelbooking.model.User;
import kz.narxoz.hotelbooking.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Override
    public LoginResponseDto login(LoginRequestDto dto, HttpServletRequest request) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        User user = (User) auth.getPrincipal();
        return userMapper.toLoginDto(user);
    }
}
