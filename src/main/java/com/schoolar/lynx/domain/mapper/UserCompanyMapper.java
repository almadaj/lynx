package com.schoolar.lynx.domain.mapper;

import com.schoolar.lynx.domain.dto.UserCompanyResponse;
import com.schoolar.lynx.domain.model.UserCompany;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserCompanyMapper {
    public UserCompanyResponse toResponse(UserCompany entity) {
        UserCompanyResponse response = new UserCompanyResponse();

        response.setCompanyId(entity.getCompany().getId());
        response.setCompanyName(entity.getCompany().getCompanyName());
        response.setPublicName(entity.getCompany().getPublicName());
        response.setRole(entity.getRole());
        response.setActive(entity.getActive());

        return response;
    }

    public List<UserCompanyResponse> toResponseList(List<UserCompany> entities) {
        return entities.stream()
                .map(this::toResponse)
                .toList();
    }
}