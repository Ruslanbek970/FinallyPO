package kz.narxoz.hotelbooking.mapper;

import kz.narxoz.hotelbooking.dto.response.LoginResponseDto;
import kz.narxoz.hotelbooking.dto.response.UserResponseDto;
import kz.narxoz.hotelbooking.model.Role;
import kz.narxoz.hotelbooking.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toDto(User user);
    List<UserResponseDto> toDtoList(List<User> users);
    LoginResponseDto toLoginDto(User user);

    default List<String> mapRoles(List<Role> roles) {
        if (roles == null) {
            return List.of();
        }
        return roles.stream()
                .map(r -> r == null ? null : r.getName())
                .toList();
    }
}
