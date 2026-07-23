package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.dto.UserCompanyResponse;
import com.schoolar.lynx.domain.model.User;
import com.schoolar.lynx.repository.UserCompanyRepository;
import com.schoolar.lynx.security.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCompanyService {
    private final AuthenticatedUserService authUserService;
    private final UserCompanyRepository userCompanyRepository;

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
}
