package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.dto.UserCompanyResponse;
import com.schoolar.lynx.domain.dto.UserDTO;
import com.schoolar.lynx.domain.dto.UserResponseDTO;
import com.schoolar.lynx.domain.mapper.UserMapper;
import com.schoolar.lynx.domain.model.User;
import com.schoolar.lynx.repository.UserRepository;
import com.schoolar.lynx.security.AuthenticatedUserService;
import com.schoolar.lynx.storage.RailwayStorageService;
import com.schoolar.lynx.utils.MapperUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final RailwayStorageService storageService;
    private final AuthenticatedUserService authenticatedUserService;
    private final UserCompanyService userCompanyService;

    public UserResponseDTO findById(UUID id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));

        if (!user.isActive()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuário não encontrado"
            );
        }

        UserResponseDTO dto = MapperUtil.parseObject(user, UserResponseDTO.class);
        dto.setProfilePhoto(
                storageService.getUrl(user.getProfilePhoto())
        );
        return dto;
    }

    public UserResponseDTO findByEmail(String email) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));

        if (!user.isActive()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuário não encontrado"
            );
        }

        UserResponseDTO dto = MapperUtil.parseObject(user, UserResponseDTO.class);
        dto.setProfilePhoto(
                storageService.getUrl(user.getProfilePhoto())
        );
        return dto;
    }

    public UserResponseDTO update(UserDTO dto, UserDTO sessionUser) {
        var user = repository.findById(dto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        var session = repository.findById(sessionUser.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário de sessão não encontrado"));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setProfilePhoto(dto.getProfilePhoto());
        user.setActive(dto.isActive());

        if (sessionUser.equals(dto)){
            user.setPassword(dto.getPassword());
            user.setBirth(LocalDate.from(dto.getBirth().atStartOfDay()));
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

    @Transactional
    public void uploadProfilePhoto(UUID id, MultipartFile file) throws IOException {
        var user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        User loggedUser = authenticatedUserService.get();

        if (loggedUser.getId() != user.getId()){
            throw new RuntimeException("É possível alterar somente a própria foto de usuário");
        }

        if (user.getProfilePhoto() != null) {
            storageService.delete(user.getProfilePhoto());
        }

        String key = storageService.upload(file, "users/" + user.getId());
        user.setProfilePhoto(key);
        repository.save(user);

        log.info(
                "Foto de perfil adicionada. userId={}, key={}",
                id,
                user.getProfilePhoto()
        );
    }

    @Transactional
    public void deleteProfilePhoto(UUID userId){
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        User loggedUser = authenticatedUserService.get();

        if (loggedUser.getId() != user.getId()){
            throw new RuntimeException("Somente é alterar foto a própria foto de usuário");
        }

        if (user.getProfilePhoto() == null) {
            return;
        }

        log.info(
                "Foto de perfil removida. userId={}, key={}",
                userId,
                user.getProfilePhoto()
        );

        storageService.delete(user.getProfilePhoto());
        user.setProfilePhoto(null);
        repository.save(user);

    }

    public UserResponseDTO getOwnInfo(){
        User loggedUser = authenticatedUserService.get();
        User user = repository.findById(loggedUser.getId())
                .orElseThrow(()-> new RuntimeException("Usuário não encontrado"));

        user.setProfilePhoto(storageService.getUrl(String.valueOf(user.getProfilePhoto())));
        List<UserCompanyResponse> companies = userCompanyService.getMyCompaniesAndRoles();

        UserResponseDTO dto = UserMapper.toResponseDTO(user);
        dto.setCompanies(companies);
        return dto;
    }
}
