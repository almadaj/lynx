package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.dto.UserResponseDTO;
import com.schoolar.lynx.domain.model.User;
import com.schoolar.lynx.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    private UUID userId;
    private User user;

    @BeforeEach
    void setup() {
        userId = UUID.randomUUID();

        user = new User();
        user.setId(userId);
        user.setName("John");
        user.setEmail("john@email.com");
        user.setActive(true);
    }

    // ===============================
    // findById - sucesso
    // ===============================
    @Test
    void shouldReturnUserWhenFound() {
        when(repository.findById(userId)).thenReturn(Optional.of(user));

        UserResponseDTO response = service.findById(userId);

        assertNotNull(response);
        verify(repository, times(1)).findById(userId);
    }

    // ===============================
    // findById - usuário inexistente
    // ===============================
    @Test
    void shouldThrow404WhenUserNotFound() {
        when(repository.findById(userId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> service.findById(userId)
        );

        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        Assertions.assertEquals("Usuário não encontrado", exception.getReason());
    }


    // ===============================
    // findById - usuário inativo
    // ===============================
    @Test
    void shouldThrow404WhenUserInactive() {
        user.setActive(false);
        when(repository.findById(userId)).thenReturn(Optional.of(user));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> service.findById(userId)
        );

        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        Assertions.assertEquals("Usuário não existe mais", exception.getReason());
    }

    // ===============================
    // deleteById - sucesso (soft delete)
    // ===============================
    @Test
    void shouldDeactivateUserOnDelete() {
        when(repository.findById(userId)).thenReturn(Optional.of(user));

        service.deleteById(userId);

        assertFalse(user.isActive());
        Mockito.verify(repository).save(user);
    }
}
