package com.schoolar.lynx.controller;

import com.schoolar.lynx.domain.dto.UserDTO;
import com.schoolar.lynx.domain.dto.UserResponseDTO;
import com.schoolar.lynx.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService service;

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
}
