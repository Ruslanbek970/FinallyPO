package kz.narxoz.hotelbooking.service.impl;

import kz.narxoz.hotelbooking.dto.request.ChangePasswordRequestDto;
import kz.narxoz.hotelbooking.dto.request.RegisterRequestDto;
import kz.narxoz.hotelbooking.dto.request.UpdateProfileRequestDto;
import kz.narxoz.hotelbooking.dto.response.UserResponseDto;
import kz.narxoz.hotelbooking.mapper.UserMapper;
import kz.narxoz.hotelbooking.model.Role;
import kz.narxoz.hotelbooking.model.User;
import kz.narxoz.hotelbooking.repository.RoleRepository;
import kz.narxoz.hotelbooking.repository.UserRepository;
import kz.narxoz.hotelbooking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(RegisterRequestDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Role roleUser = roleRepository.findByName("ROLE_USER").orElse(null);
        if (Objects.isNull(roleUser)) {
            throw new RuntimeException("ROLE_USER not found");
        }

        User u = new User();
        u.setEmail(dto.getEmail());
        u.setPassword(passwordEncoder.encode(dto.getPassword()));
        u.setFullName(dto.getFullName());
        u.setPhone(dto.getPhone());
        u.setActive(true);
        u.setRoles(List.of(roleUser));

        User saved = userRepository.save(u);
        return userMapper.toDto(saved);
    }

    @Override
    public UserResponseDto me() {
        User user = getCurrentUserEntity();
        if (user == null) return null;
        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto updateProfile(UpdateProfileRequestDto dto) {
        User user = getCurrentUserEntity();
        if (user == null) return null;

        if (dto.getFullName() != null && !dto.getFullName().isBlank()) {
            user.setFullName(dto.getFullName());
        }
        if (dto.getPhone() != null && !dto.getPhone().isBlank()) {
            user.setPhone(dto.getPhone());
        }

        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    @Override
    public void changePassword(ChangePasswordRequestDto dto) {
        User user = getCurrentUserEntity();
        if (user == null) throw new RuntimeException("Unauthorized");

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password incorrect");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public User getCurrentUserEntity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) return null;

        Object principal = auth.getPrincipal();
        if (principal instanceof User u) {
            return u;
        }

        String email = auth.getName();
        return userRepository.findByEmail(email).orElse(null);
    }
}
