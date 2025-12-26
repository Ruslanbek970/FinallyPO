package kz.narxoz.hotelbooking.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCreateUserRequestDto {
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String role;
}
