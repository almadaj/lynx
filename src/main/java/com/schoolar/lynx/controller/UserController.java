package com.schoolar.lynx.controller;

import com.schoolar.lynx.domain.dto.UserDTO;
import com.schoolar.lynx.domain.dto.UserResponseDTO;
import com.schoolar.lynx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")

public class UserController {
    @Autowired
    private UserService service;

    @PostMapping
    public UserResponseDTO createUser(@RequestBody UserDTO user){
        return service.create(user);
    }

    @PutMapping
    public UserResponseDTO updateUser (@RequestBody UserDTO user, @RequestBody UserDTO sessionUser){
        return service.update(user, sessionUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser (@PathVariable UUID id){
        service.deleteById(id);
    }

    @GetMapping("/{id}")
    public UserResponseDTO findUserById(@PathVariable UUID id){
        return service.findById(id);
    }
}
