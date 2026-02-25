package com.schoolar.lynx.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company")
@Builder
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "public_name", length = 150, nullable = false)
    private String publicName;

    @Column(name = "company_name", length = 400, nullable = false)
    private String companyName;

    @Column(nullable = false, unique = true)
    @Email(message = "Email inválido")
    private String email;

    @Column
    private String phone;

    @Column(nullable = false, unique = true)
    private String cnpj;

    @Column
    private String address;

    @Column(name = "has_online")
    private boolean hasOnlineClass;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "principal_teacher_id")
    private User principalTeacher;
}

