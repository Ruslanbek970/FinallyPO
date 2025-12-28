package kz.narxoz.hotelbooking.service;

import kz.narxoz.hotelbooking.dto.request.RegisterRequestDto;
import kz.narxoz.hotelbooking.dto.response.UserResponseDto;
import kz.narxoz.hotelbooking.mapper.UserMapper;
import kz.narxoz.hotelbooking.model.Role;
import kz.narxoz.hotelbooking.model.User;
import kz.narxoz.hotelbooking.repository.RoleRepository;
import kz.narxoz.hotelbooking.repository.UserRepository;
import kz.narxoz.hotelbooking.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Test
    void registerWhenEmailAlreadyUsed() {
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        UserMapper userMapper = mock(UserMapper.class);

        UserService userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder, userMapper);

        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setEmail("user@test.com");

        when(userRepository.existsByEmail("user@test.com")).thenReturn(true);

        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> userService.register(dto));
        Assertions.assertEquals("Email already exists", ex.getMessage());

        verify(userRepository, times(1)).existsByEmail("user@test.com");
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).toDto(any());
    }

    @Test
    void registerWhenRoleNotFound() {
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        UserMapper userMapper = mock(UserMapper.class);

        UserService userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder, userMapper);

        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setEmail("user@test.com");
        dto.setPassword("123");
        dto.setFullName("User Name");
        dto.setPhone("+7700");

        when(userRepository.existsByEmail("user@test.com")).thenReturn(false);
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.empty());

        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> userService.register(dto));
        Assertions.assertEquals("ROLE_USER not found", ex.getMessage());

        verify(roleRepository, times(1)).findByName("ROLE_USER");
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).toDto(any());
    }

    @Test
    void registerSuccess() {
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        UserMapper userMapper = mock(UserMapper.class);

        UserService userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder, userMapper);

        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setEmail("user@test.com");
        dto.setPassword("123");
        dto.setFullName("User Name");
        dto.setPhone("+7700");

        when(userRepository.existsByEmail("user@test.com")).thenReturn(false);

        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));

        // чтобы не было предупреждения "might be null"
        doReturn("ENC").when(passwordEncoder).encode("123");

        User saved = new User();
        saved.setId(10L);
        saved.setEmail("user@test.com");

        when(userRepository.save(any(User.class))).thenReturn(saved);
        UserResponseDto response = UserResponseDto.builder()
                .id(10L)
                .email("user@test.com")
                .build();

        when(userMapper.toDto(saved)).thenReturn(response);

        UserResponseDto result = userService.register(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(10L, result.getId());
        Assertions.assertEquals("user@test.com", result.getEmail());

        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).toDto(saved);
    }
}
