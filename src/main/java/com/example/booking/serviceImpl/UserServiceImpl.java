package com.example.booking.serviceImpl;

import com.example.booking.Dto.request.UserRequestDto;
import com.example.booking.Dto.response.UserResponseDto;
import com.example.booking.Entity.Role;
import com.example.booking.Entity.User;
import com.example.booking.Enum.UserStatus;
import com.example.booking.Mapper.UserMapper;
import com.example.booking.Service.UserService;
import com.example.booking.repository.RoleRepository;
import com.example.booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRequestDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        User user = userMapper.toEntity(dto);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto createByAdmin(UserRequestDto dto, Set<Long> roleIds) {
        User user = userMapper.toEntity(dto);
        if (roleIds != null) {
            Set<Role> roles = new HashSet<>(roleRepository.findAllById(roleIds));
            user.setRoles(roles);
        }
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto updateProfile(Long userId, UserRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public UserResponseDto changeStatus(Long userId, UserStatus status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setStatus(status);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto updateRoles(Long userId, Set<Long> roleIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Set<Role> roles = new HashSet<>(roleRepository.findAllById(roleIds));
        user.setRoles(roles);

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto getById(Long id) {
        return userMapper.toDto(
                userRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"))
        );
    }

    @Override
    public List<UserResponseDto> getAll() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    @Override
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.deleteById(id);
    }
}
