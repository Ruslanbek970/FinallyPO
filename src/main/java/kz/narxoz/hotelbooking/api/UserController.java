package kz.narxoz.hotelbooking.api;

import kz.narxoz.hotelbooking.dto.request.ChangePasswordRequestDto;
import kz.narxoz.hotelbooking.dto.request.UpdateProfileRequestDto;
import kz.narxoz.hotelbooking.dto.response.UserResponseDto;
import kz.narxoz.hotelbooking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER','ADMIN','MANAGER')")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> me() {
        return ResponseEntity.ok(userService.me());
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponseDto> updateProfile(@RequestBody UpdateProfileRequestDto dto) {
        return ResponseEntity.ok(userService.updateProfile(dto));
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequestDto dto) {
        userService.changePassword(dto);
        return ResponseEntity.ok().build();
    }
}
