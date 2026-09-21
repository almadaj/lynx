package com.schoolar.lynx.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", schema = "seguranca")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(length = 150, nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    @Email(message = "Email inválido")
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private LocalDate birth;

    @Column(name = "profile_photo")
    private String profilePhoto;

    @Column(name = "is_teacher", nullable = false)
    private boolean isTeacher;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

}
