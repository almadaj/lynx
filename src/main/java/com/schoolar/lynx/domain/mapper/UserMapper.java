package com.schoolar.lynx.domain.mapper;

import com.schoolar.lynx.domain.dto.UserResponseDTO;
import com.schoolar.lynx.domain.model.User;

public class UserMapper {

    public static UserResponseDTO toResponseDTO(User user) {
        if (user == null) {
            return null;
        }

        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setBirth(user.getBirth());
        dto.setProfilePhoto(user.getProfilePhoto());
        dto.setActive(user.isActive());
        dto.setAdmin(user.isAdmin());

        return dto;
    }
}