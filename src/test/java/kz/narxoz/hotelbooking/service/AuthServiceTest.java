package kz.narxoz.hotelbooking.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kz.narxoz.hotelbooking.dto.request.LoginRequestDto;
import kz.narxoz.hotelbooking.dto.response.LoginResponseDto;
import kz.narxoz.hotelbooking.mapper.UserMapper;
import kz.narxoz.hotelbooking.model.Role;
import kz.narxoz.hotelbooking.model.User;
import kz.narxoz.hotelbooking.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserMapper userMapper;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @InjectMocks
    private AuthServiceImpl authService;

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void login_shouldAuthenticate_setSecurityContext_createSession_andReturnDto() {


        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail("user@test.com");
        dto.setPassword("123");

        Role role = new Role();
        role.setName("ROLE_USER");

        User principal = new User();
        principal.setId(1L);
        principal.setEmail("user@test.com");
        principal.setFullName("User Name");
        principal.setRoles(List.of(role));

        Authentication auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(request.getSession(true)).thenReturn(session);

        LoginResponseDto expected = LoginResponseDto.builder()
                .id(1L)
                .email("user@test.com")
                .fullName("User Name")
                .roles(List.of("ROLE_USER"))
                .build();

        when(userMapper.toLoginDto(principal)).thenReturn(expected);


        LoginResponseDto result = authService.login(dto, request);


        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("user@test.com", result.getEmail());


        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));


        verify(request).getSession(true);


        Assertions.assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        Assertions.assertEquals("user@test.com", SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
