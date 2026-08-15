package com.schoolar.lynx.controller;

import com.schoolar.lynx.domain.dto.UserDTO;
import com.schoolar.lynx.domain.dto.UserResponseDTO;
import com.schoolar.lynx.domain.enums.Role;
import com.schoolar.lynx.service.UserService;
import com.schoolar.lynx.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService service;

    @Autowired
    private final StorageService storageService;

    @PutMapping
    public UserResponseDTO updateUser (@RequestBody UserDTO user, @RequestBody UserDTO sessionUser){
        return service.update(user, sessionUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser (@PathVariable UUID id){
        service.deleteById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/email={email}")
    public ResponseEntity<UserResponseDTO> findUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.findByEmail(email));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> findMyInfo(){
        return ResponseEntity.ok(service.getOwnInfo());
    }

    @PostMapping("/{id}/photo")
    public ResponseEntity<Void> uploadProfilePhoto(
            @PathVariable UUID id,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        service.uploadProfilePhoto(id, file);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/photo")
    public ResponseEntity<Void> deleteProfilePhoto(
            @PathVariable UUID id
    ) throws IOException {
        service.deleteProfilePhoto(id);
        return ResponseEntity.noContent().build();
    }
}
