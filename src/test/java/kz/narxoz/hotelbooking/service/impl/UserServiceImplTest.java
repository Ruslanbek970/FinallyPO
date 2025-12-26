package kz.narxoz.hotelbooking.service.impl;

import kz.narxoz.hotelbooking.dto.request.RegisterRequestDto;
import kz.narxoz.hotelbooking.mapper.UserMapper;
import kz.narxoz.hotelbooking.model.Role;
import kz.narxoz.hotelbooking.model.User;
import kz.narxoz.hotelbooking.repository.RoleRepository;
import kz.narxoz.hotelbooking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder encoder;
    private UserMapper userMapper;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        encoder = mock(PasswordEncoder.class);
        userMapper = mock(UserMapper.class);

        userService = new UserServiceImpl(userRepository, roleRepository, encoder, userMapper);
    }

    @Test
    void register_shouldCreateUserWithRoleUser() {
        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setEmail("new@booking.kz");
        dto.setPassword("123");
        dto.setFullName("New User");

        Role roleUser = new Role();
        roleUser.setId(3L);
        roleUser.setName("ROLE_USER");

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(roleUser));
        when(encoder.encode("123")).thenReturn("HASH");

        userService.register(dto);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());

        User created = captor.getValue();
        assertEquals("new@booking.kz", created.getEmail());
        assertEquals("HASH", created.getPassword());
        assertTrue(created.isActive());
        assertEquals(1, created.getRoles().size());
        assertEquals("ROLE_USER", created.getRoles().get(0).getName());
    }

    @Test
    void register_shouldThrowIfEmailExists() {
        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setEmail("exists@booking.kz");

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.register(dto));
        verify(userRepository, never()).save(any());
    }
}
