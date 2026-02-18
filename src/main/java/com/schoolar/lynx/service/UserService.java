package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.dto.UserDTO;
import com.schoolar.lynx.domain.dto.UserResponseDTO;
import com.schoolar.lynx.domain.model.User;
import com.schoolar.lynx.repository.UserRepository;
import com.schoolar.lynx.utils.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository repository;
    UserService(UserRepository repository){
        this.repository = repository;
    }

    public UserResponseDTO create(UserDTO dto){
        var userEntity = MapperUtil.parseObject(dto, User.class);
        var savedUser = repository.save(userEntity);
        return MapperUtil.parseObject(savedUser, UserResponseDTO.class);
    }

    public UserResponseDTO findById(UUID id){
        var user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return MapperUtil.parseObject(user, UserResponseDTO.class);
    }

    public UserResponseDTO update(UserDTO dto, UserDTO sessionUser) {
        var user = repository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        var session = repository.findById(sessionUser.getId())
                .orElseThrow(() -> new RuntimeException("Usuário de sessão não encontrado"));

        if (!sessionUser.getIsAdmin() && Boolean.TRUE.equals(dto.getIsAdmin())) {
            throw new RuntimeException("Você não tem permissão para promover usuário a admin.");
        }
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setActive(dto.getIsActive());

        if (sessionUser.equals(dto)){
            user.setPassword(dto.getPassword());
            user.setBirth(dto.getBirth().atStartOfDay());
        }
        if (sessionUser.getIsAdmin()) {
            user.setAdmin(dto.getIsAdmin());
        }

        var savedUser = repository.save(user);
        return MapperUtil.parseObject(savedUser, UserResponseDTO.class);
    }

    public void deleteById(UUID id){
        var user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        user.setActive(false);
        repository.save(user);
    }
}
