package kz.narxoz.hotelbooking.mapper;

import kz.narxoz.hotelbooking.dto.response.UserResponseDto;
import kz.narxoz.hotelbooking.model.Role;
import kz.narxoz.hotelbooking.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void convertEntityToDto() {
        Role r1 = new Role();
        r1.setName("ROLE_USER");

        Role r2 = new Role();
        r2.setName("ROLE_ADMIN");

        User user = new User();
        user.setId(1L);
        user.setEmail("user@test.com");
        user.setFullName("User Name");
        user.setPhone("+7700");
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setRoles(List.of(r1, r2));

        UserResponseDto dto = userMapper.toDto(user);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(1L, dto.getId());
        Assertions.assertEquals("user@test.com", dto.getEmail());
        Assertions.assertEquals("User Name", dto.getFullName());
        Assertions.assertEquals("+7700", dto.getPhone());
        Assertions.assertTrue(dto.isActive());
        Assertions.assertNotNull(dto.getCreatedAt());
        Assertions.assertNotNull(dto.getRoles());
        Assertions.assertEquals(2, dto.getRoles().size());
        Assertions.assertEquals("ROLE_USER", dto.getRoles().get(0));
        Assertions.assertEquals("ROLE_ADMIN", dto.getRoles().get(1));
    }

    @Test
    void mapRolesWhenNull() {
        List<String> roles = userMapper.mapRoles(null);
        Assertions.assertNotNull(roles);
        Assertions.assertEquals(0, roles.size());
    }

    @Test
    void mapRolesWhenHasNullElement() {
        Role r1 = new Role();
        r1.setName("ROLE_USER");

        List<Role> rolesSrc = new ArrayList<>();
        rolesSrc.add(r1);
        rolesSrc.add(null);

        List<String> roles = userMapper.mapRoles(rolesSrc);

        Assertions.assertNotNull(roles);
        Assertions.assertEquals(2, roles.size());
        Assertions.assertEquals("ROLE_USER", roles.get(0));
        Assertions.assertNull(roles.get(1));
    }
}
