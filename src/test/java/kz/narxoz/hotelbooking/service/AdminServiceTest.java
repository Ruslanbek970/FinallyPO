package kz.narxoz.hotelbooking.service;

import kz.narxoz.hotelbooking.dto.request.AdminCreateUserRequestDto;
import kz.narxoz.hotelbooking.dto.response.UserResponseDto;
import kz.narxoz.hotelbooking.mapper.UserMapper;
import kz.narxoz.hotelbooking.model.Role;
import kz.narxoz.hotelbooking.model.User;
import kz.narxoz.hotelbooking.repository.RoleRepository;
import kz.narxoz.hotelbooking.repository.UserRepository;
import kz.narxoz.hotelbooking.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class AdminServiceTest {

    @Test
    void createUserWhenAlreadyExists() {
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        UserMapper userMapper = mock(UserMapper.class);

        AdminService adminService = new AdminServiceImpl(userRepository, roleRepository, passwordEncoder, userMapper);

        AdminCreateUserRequestDto dto = new AdminCreateUserRequestDto();
        dto.setEmail("admin@test.com");
        dto.setPassword("123");

        when(userRepository.findByEmail("admin@test.com")).thenReturn(Optional.of(new User()));

        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> adminService.createUser(dto));
        Assertions.assertEquals("User already exists", ex.getMessage());

        verify(userRepository, times(1)).findByEmail("admin@test.com");
        verify(roleRepository, never()).findByName(anyString());
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).toDto(any());
    }

    @Test
    void createUserWhenRoleNotFound() {
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        UserMapper userMapper = mock(UserMapper.class);

        AdminService adminService = new AdminServiceImpl(userRepository, roleRepository, passwordEncoder, userMapper);

        AdminCreateUserRequestDto dto = new AdminCreateUserRequestDto();
        dto.setEmail("admin@test.com");
        dto.setPassword("123");

        when(userRepository.findByEmail("admin@test.com")).thenReturn(Optional.empty());
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.empty());

        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> adminService.createUser(dto));
        Assertions.assertEquals("ROLE_USER not found", ex.getMessage());

        verify(userRepository, times(1)).findByEmail("admin@test.com");
        verify(roleRepository, times(1)).findByName("ROLE_USER");
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).toDto(any());
    }

    @Test
    void createUserSuccess() {
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        UserMapper userMapper = mock(UserMapper.class);

        AdminService adminService = new AdminServiceImpl(userRepository, roleRepository, passwordEncoder, userMapper);

        AdminCreateUserRequestDto dto = new AdminCreateUserRequestDto();
        dto.setEmail("manager@test.com");
        dto.setPassword("123");
        dto.setFullName("Manager Name");
        dto.setPhone("+7700");

        when(userRepository.findByEmail("manager@test.com")).thenReturn(Optional.empty());

        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));

        // чтобы не было предупреждения "might be null"
        doReturn("ENC").when(passwordEncoder).encode("123");

        User saved = new User();
        saved.setId(10L);
        saved.setEmail("manager@test.com");
        saved.setFullName("Manager Name");
        saved.setPhone("+7700");
        saved.setActive(true);
        saved.setRoles(List.of(role));

        when(userRepository.save(any(User.class))).thenReturn(saved);

        // Важно: теперь сервис возвращает userMapper.toDto(saved)
        UserResponseDto mapped = UserResponseDto.builder()
                .id(10L)
                .email("manager@test.com")
                .fullName("Manager Name")
                .phone("+7700")
                .active(true)
                .roles(List.of("ROLE_USER"))
                .build();

        when(userMapper.toDto(saved)).thenReturn(mapped);

        UserResponseDto result = adminService.createUser(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(10L, result.getId());
        Assertions.assertEquals("manager@test.com", result.getEmail());
        Assertions.assertEquals("Manager Name", result.getFullName());
        Assertions.assertEquals("+7700", result.getPhone());
        Assertions.assertTrue(result.isActive());
        Assertions.assertNotNull(result.getRoles());
        Assertions.assertEquals(1, result.getRoles().size());
        Assertions.assertEquals("ROLE_USER", result.getRoles().get(0));

        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).toDto(saved);
    }

    @Test
    void blockUserWhenNotFound() {
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        UserMapper userMapper = mock(UserMapper.class);

        AdminService adminService = new AdminServiceImpl(userRepository, roleRepository, passwordEncoder, userMapper);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = adminService.blockUser(1L);

        Assertions.assertFalse(result);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).toDto(any());
    }

    @Test
    void blockUserSuccess() {
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        UserMapper userMapper = mock(UserMapper.class);

        AdminService adminService = new AdminServiceImpl(userRepository, roleRepository, passwordEncoder, userMapper);

        User user = new User();
        user.setId(1L);
        user.setActive(true);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        boolean result = adminService.blockUser(1L);

        Assertions.assertTrue(result);
        Assertions.assertFalse(user.isActive());

        verify(userRepository, times(1)).save(user);
        verify(userMapper, never()).toDto(any());
    }
}
