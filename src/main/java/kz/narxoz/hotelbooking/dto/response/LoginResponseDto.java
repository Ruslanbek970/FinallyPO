package kz.narxoz.hotelbooking.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoginResponseDto {
    private Long id;
    private String email;
    private String fullName;
    private List<String> roles;
}
