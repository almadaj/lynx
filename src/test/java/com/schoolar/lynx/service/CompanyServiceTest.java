package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.dto.CompanyResponseDTO;
import com.schoolar.lynx.domain.dto.RegisterCompanyDTO;
import com.schoolar.lynx.domain.model.Company;
import com.schoolar.lynx.domain.model.User;
import com.schoolar.lynx.repository.CompanyRepository;
import com.schoolar.lynx.repository.UserRepository;
import com.schoolar.lynx.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {
    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CompanyService service;

    private UUID companyId;
    private UUID principalId;

    private Company company;
    private User principal;

    @BeforeEach
    void setup() {
        companyId = UUID.randomUUID();
        principalId = UUID.randomUUID();

        principal = new User();
        principal.setId(principalId);

        company = new Company();
        company.setId(companyId);
        company.setPublicName("Sample School");
        company.setCompanyName("Sample School Inc.");
        company.setCnpj("0000000000000");
        company.setEmail("company@inc.com");
        company.setActive(true);
        company.setPrincipalTeacher(principal);
    }

    @Test
    void shouldCreateCompanySuccessfully() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);

        when(userDetails.getUsername()).thenReturn("user@email.com");

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        SecurityContextHolder.setContext(securityContext);

        principal.setAdmin(true);

        RegisterCompanyDTO dto = new RegisterCompanyDTO();
        dto.setPublicName("Sample School");
        dto.setCompanyName("Sample School Inc.");
        dto.setCnpj("0000000000000");
        dto.setEmail("company@inc.com");

        when(userRepository.findByEmail("user@email.com"))
                .thenReturn(Optional.of(principal));

        when(userRepository.findById(principalId))
                .thenReturn(Optional.of(principal));

        when(companyRepository.existsByEmail(anyString())).thenReturn(false);
        when(companyRepository.existsByCnpj(anyString())).thenReturn(false);

        when(companyRepository.save(any(Company.class)))
                .thenReturn(company);

        CompanyResponseDTO response = service.create(dto);

        assertNotNull(response);
        assertEquals("Sample School", response.getPublicName());

        verify(companyRepository).save(any(Company.class));
    }

}


