package edu.mx.unsis.unsiSmile.controller.users;

import edu.mx.unsis.unsiSmile.dtos.response.users.UserBaseResponse;
import edu.mx.unsis.unsiSmile.dtos.response.users.UserResponse;
import edu.mx.unsis.unsiSmile.service.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/unsismile/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Obtiene información del perfil del usuario autenticado")
    @GetMapping("/profile")
    public UserResponse getProfile() {
        return userService.getCurrentUser();
    }

    @Operation(summary = "Obtiene la información extendida del usuario autenticado")
    @GetMapping("/user-information")
    public ResponseEntity<?> getInformationUser() {
        return userService.getInformationUser();
    }

    @Operation(summary = "Actualiza o crea la imagen de perfil del usuario")
    @PatchMapping("/update-profile-picture")
    public ResponseEntity<Void> updateProfilePicture(@RequestPart MultipartFile picture) {
        userService.createOrUpdateProfilePicture(picture);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Obtiene la imagen de perfil del usuario autenticado")
    @GetMapping("/profile-picture")
    public ResponseEntity<byte[]> getProfilePicture() {
        return userService.getProfilePicture();
    }

    @Operation(summary = "Obtiene información de usuario por username")
    @GetMapping("/username/{username}")
    public UserBaseResponse getUserByUsername(@PathVariable String username) {
        return userService.getUserBaseByUsername(username);
    }
}