package kz.narxoz.hotelbooking.service.impl;

import kz.narxoz.hotelbooking.dto.request.AdminCreateUserRequestDto;
import kz.narxoz.hotelbooking.dto.response.UserResponseDto;
import kz.narxoz.hotelbooking.model.Role;
import kz.narxoz.hotelbooking.model.User;
import kz.narxoz.hotelbooking.repository.RoleRepository;
import kz.narxoz.hotelbooking.repository.UserRepository;
import kz.narxoz.hotelbooking.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceImplTest {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder encoder;

    private AdminServiceImpl adminService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        encoder = mock(PasswordEncoder.class);

        adminService = new AdminServiceImpl(userRepository, roleRepository, encoder);
    }

    @Test
    void createUser_shouldThrowIfUserExists() {
        AdminCreateUserRequestDto dto = new AdminCreateUserRequestDto();
        dto.setEmail("exists@booking.kz");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(new User()));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> adminService.createUser(dto));
        assertEquals("User already exists", ex.getMessage());
    }

    @Test
    void createUser_shouldCreateUser() {
        AdminCreateUserRequestDto dto = new AdminCreateUserRequestDto();
        dto.setEmail("u@booking.kz");
        dto.setPassword("123");
        dto.setFullName("User");
        dto.setPhone("+777");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());

        Role roleUser = new Role();
        roleUser.setId(3L);
        roleUser.setName("ROLE_USER");

        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(roleUser));
        when(encoder.encode("123")).thenReturn("HASH");

        User saved = new User();
        saved.setId(10L);
        saved.setEmail(dto.getEmail());
        saved.setPassword("HASH");
        saved.setFullName(dto.getFullName());
        saved.setPhone(dto.getPhone());
        saved.setActive(true);
        saved.setRoles(java.util.List.of(roleUser));

        when(userRepository.save(any(User.class))).thenReturn(saved);

        UserResponseDto res = adminService.createUser(dto);

        assertNotNull(res);
        assertEquals(10L, res.getId());
        assertEquals("u@booking.kz", res.getEmail());
        assertTrue(res.getRoles().contains("ROLE_USER"));
    }

    @Test
    void deleteUser_shouldDeleteIfExists() {
        User u = new User();
        u.setId(5L);

        when(userRepository.findById(5L)).thenReturn(Optional.of(u));

        assertTrue(adminService.deleteUser(5L));
        verify(userRepository).deleteById(5L);
    }
}
