package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.dto.AddNewMemberDTO;
import com.schoolar.lynx.domain.dto.UserCompanyResponse;
import com.schoolar.lynx.domain.dto.UserResponseDTO;
import com.schoolar.lynx.domain.enums.Role;
import com.schoolar.lynx.domain.mapper.UserCompanyMapper;
import com.schoolar.lynx.domain.model.Company;
import com.schoolar.lynx.domain.model.User;
import com.schoolar.lynx.domain.model.UserCompany;
import com.schoolar.lynx.repository.CompanyRepository;
import com.schoolar.lynx.repository.UserCompanyRepository;
import com.schoolar.lynx.repository.UserRepository;
import com.schoolar.lynx.security.AuthenticatedUserService;
import com.schoolar.lynx.utils.MapperUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserCompanyService {
    private final AuthenticatedUserService authUserService;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final UserCompanyRepository userCompanyRepository;
    private final CompanyAuthorizationService authorizationService;
    private final UserCompanyMapper userCompanyMapper;

    public List<UserCompanyResponse> getMyCompaniesAndRoles() {
        User loggedUser = authUserService.get();

        return userCompanyRepository.findAllByUserId(loggedUser.getId())
                .stream()
                .map(userCompany -> UserCompanyResponse.builder()
                        .companyId(userCompany.getCompany().getId())
                        .companyName(userCompany.getCompany().getCompanyName())
                        .publicName(userCompany.getCompany().getPublicName())
                        .role(userCompany.getRole())
                        .build())
                .toList();
    }

    @Transactional
    public UserCompanyResponse addTeacherToCompany(UUID companyId, AddNewMemberDTO dto) {
        authorizationService.require(Role.HEADTEACHER, companyId);

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Empresa não encontrada"
                ));
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));

        if (dto.getRole() == Role.ADMIN || dto.getRole() == Role.PRINCIPAL ) {
            throw new IllegalArgumentException("Papel administrativo inválido.");
        }

        Optional<UserCompany> existingUserCompany =
                userCompanyRepository.findByUserIdAndCompanyId(user.getId(), companyId);
        if (existingUserCompany.isPresent()) {
            UserCompany userCompany = existingUserCompany.get();

            if (userCompany.getActive()) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Usuário já pertence a esta empresa."
                );
            }

            userCompany.setActive(true);
            userCompany.setRole(dto.getRole());

            UserCompany saved = userCompanyRepository.save(userCompany);
            return userCompanyMapper.toResponse(saved);
        }

        UserCompany userCompany = new UserCompany();
        userCompany.setCompany(company);
        userCompany.setUser(user);
        userCompany.setRole(dto.getRole());

        UserCompany saved = userCompanyRepository.save(userCompany);

        return userCompanyMapper.toResponse(saved);
    }

    @Transactional
    public UserCompanyResponse addStudentToCompany(UUID companyId, String email) {
        authorizationService.require(Role.HEADTEACHER, companyId);

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Empresa não encontrada"
                ));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));

        Optional<UserCompany> existingUserCompany =
                userCompanyRepository.findByUserIdAndCompanyId(user.getId(), companyId);
        if (existingUserCompany.isPresent()) {
            UserCompany userCompany = existingUserCompany.get();

            if (userCompany.getActive()) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Usuário já pertence a esta empresa."
                );
            }
            userCompany.setActive(true);
            userCompany.setRole(Role.STUDENT);

            UserCompany saved = userCompanyRepository.save(userCompany);
            return userCompanyMapper.toResponse(saved);
        }

        UserCompany userCompany = new UserCompany();
        userCompany.setCompany(company);
        userCompany.setUser(user);
        userCompany.setRole(Role.STUDENT);

        UserCompany saved = userCompanyRepository.save(userCompany);
        return userCompanyMapper.toResponse(saved);
    }

    @Transactional
    public UserCompanyResponse createPrincipalUserCompany(User user, Company company) {
        Optional<UserCompany> existing =
                userCompanyRepository.findByUserIdAndCompanyId(user.getId(), company.getId());

        if (existing.isPresent()) {
            UserCompany userCompany = existing.get();

            userCompany.setActive(true);
            userCompany.setRole(Role.PRINCIPAL);

            UserCompany saved = userCompanyRepository.save(userCompany);
            return userCompanyMapper.toResponse(saved);
        }

        UserCompany userCompany = new UserCompany();
        userCompany.setUser(user);
        userCompany.setCompany(company);
        userCompany.setRole(Role.PRINCIPAL);

        UserCompany saved = userCompanyRepository.save(userCompany);

        return userCompanyMapper.toResponse(saved);
    }
}
