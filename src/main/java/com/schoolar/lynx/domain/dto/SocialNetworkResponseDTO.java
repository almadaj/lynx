package com.schoolar.lynx.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SocialNetworkResponseDTO {
    private UUID id;
    private String name;
    private String icon;
}
