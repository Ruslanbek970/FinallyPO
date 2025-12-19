package com.example.booking.Dto.request;

import com.example.booking.Dto.response.RoleResponseDto;
import com.example.booking.Enum.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private UserStatus status;

    private Set<RoleResponseDto> roles; // вместо roleIds
}
