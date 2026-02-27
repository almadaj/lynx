package com.schoolar.lynx.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CompanySocialNetworkRequestDTO {
    @NotNull
    private UUID socialNetworkId;

    @NotBlank
    private String url;
}

