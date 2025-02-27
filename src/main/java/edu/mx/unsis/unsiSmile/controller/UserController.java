package edu.mx.unsis.unsiSmile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.mx.unsis.unsiSmile.dtos.response.UserResponse;
import edu.mx.unsis.unsiSmile.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/unsismile/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public UserResponse getProfile() {
        return userService.getCurrentUser();
    }

    @GetMapping("/userInformation")
    public ResponseEntity<?> getInformationUser() {
        return userService.getInformationUser();
    }

    @PatchMapping("/update-profile-picture")
    public ResponseEntity<Void> updateProfilePicture(@RequestPart MultipartFile picture) {
        userService.createOrUpdateProfilePicture(picture);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile-picture")
    public ResponseEntity<byte[]> getProfilePicture() {
        return userService.getProfilePicture();
    }
}

