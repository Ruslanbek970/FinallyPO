package kz.narxoz.hotelbooking.service.impl;

import kz.narxoz.hotelbooking.dto.request.AdminCreateUserRequestDto;
import kz.narxoz.hotelbooking.dto.response.UserResponseDto;
import kz.narxoz.hotelbooking.mapper.UserMapper;
import kz.narxoz.hotelbooking.model.Role;
import kz.narxoz.hotelbooking.model.User;
import kz.narxoz.hotelbooking.repository.RoleRepository;
import kz.narxoz.hotelbooking.repository.UserRepository;
import kz.narxoz.hotelbooking.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto createUser(AdminCreateUserRequestDto dto) {

        User check = userRepository.findByEmail(dto.getEmail()).orElse(null);
        if (check != null) {
            throw new RuntimeException("User already exists");
        }

        Role userRole = roleRepository.findByName("ROLE_USER").orElse(null);
        if (Objects.isNull(userRole)) {
            throw new RuntimeException("ROLE_USER not found");
        }

        User u = new User();
        u.setEmail(dto.getEmail());
        u.setPassword(passwordEncoder.encode(dto.getPassword()));
        u.setFullName(dto.getFullName());
        u.setPhone(dto.getPhone());
        u.setActive(true);


        u.setRoles(new ArrayList<>(List.of(userRole)));

        User saved = userRepository.save(u);

        return userMapper.toDto(saved);
    }

    @Override
    public Boolean blockUser(Long id) {
        User u = userRepository.findById(id).orElse(null);
        if (Objects.isNull(u)) return false;

        u.setActive(false);
        userRepository.save(u);
        return true;
    }

    @Override
    public Boolean deleteUser(Long id) {
        User u = userRepository.findById(id).orElse(null);
        if (Objects.isNull(u)) return false;

        userRepository.deleteById(id);
        return true;
    }

    @Override
    public Boolean setRole(Long userId, String roleName) {
        User u = userRepository.findById(userId).orElse(null);
        if (Objects.isNull(u)) return false;

        Role role = roleRepository.findByName(roleName).orElse(null);
        if (role == null) return false;

        u.setRoles(new ArrayList<>(List.of(role)));

        userRepository.save(u);
        return true;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userMapper.toDtoList(userRepository.findAll());
    }
}
