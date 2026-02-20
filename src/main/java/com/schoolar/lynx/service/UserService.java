package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.dto.UserDTO;
import com.schoolar.lynx.domain.dto.UserResponseDTO;
import com.schoolar.lynx.domain.model.User;
import com.schoolar.lynx.repository.UserRepository;
import com.schoolar.lynx.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public UserResponseDTO create(UserDTO dto){
        var userEntity = MapperUtil.parseObject(dto, User.class);
        var savedUser = repository.save(userEntity);
        return MapperUtil.parseObject(savedUser, UserResponseDTO.class);
    }

    public UserResponseDTO findById(UUID id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));

        if (!user.isActive()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuário não existe mais"
            );
        }
        return MapperUtil.parseObject(user, UserResponseDTO.class);
    }

    public UserResponseDTO update(UserDTO dto, UserDTO sessionUser) {
        var user = repository.findById(dto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        var session = repository.findById(sessionUser.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário de sessão não encontrado"));

        if (!sessionUser.isAdmin() && Boolean.TRUE.equals(dto.isAdmin())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para promover usuário a admin.");
        }
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setActive(dto.isActive());

        if (sessionUser.equals(dto)){
            user.setPassword(dto.getPassword());
            user.setBirth(dto.getBirth().atStartOfDay());
        }
        if (sessionUser.isAdmin()) {
            user.setAdmin(dto.isAdmin());
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
