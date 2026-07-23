package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.enums.Role;
import com.schoolar.lynx.domain.model.User;
import com.schoolar.lynx.repository.UserCompanyRepository;
import com.schoolar.lynx.security.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyAuthorizationService {

    private final UserCompanyRepository userCompanyRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public Role getRole(UUID companyId) {
        User user = authenticatedUserService.get();

        return userCompanyRepository
                .findByUserIdAndCompanyId(user.getId(), companyId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "Usuário não pertence à empresa."
                ))
                .getRole();
    }

    public void require(Role minimumRole, UUID companyId) {
        Role current = getRole(companyId);

        if (!current.hasPermission(minimumRole)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Permissão insuficiente");
        }
    }
}