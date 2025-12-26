package kz.narxoz.hotelbooking.mapper;

import kz.narxoz.hotelbooking.dto.response.UserResponseDto;
import kz.narxoz.hotelbooking.model.Role;
import kz.narxoz.hotelbooking.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = Role.class)
public interface UserMapper {

    @Mapping(target = "roles", expression = "java(user.getRoles().stream().map(Role::getName).toList())")
    UserResponseDto toDto(User user);
}
