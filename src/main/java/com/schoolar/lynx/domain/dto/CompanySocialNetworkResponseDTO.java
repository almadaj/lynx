package com.schoolar.lynx.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CompanySocialNetworkResponseDTO {
    private UUID id;
    private UUID socialNetworkId;
    private String name;
    private String icon;
    private String url;
}
