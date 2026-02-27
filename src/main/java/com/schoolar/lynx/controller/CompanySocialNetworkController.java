package com.schoolar.lynx.controller;

import com.schoolar.lynx.domain.dto.CompanySocialNetworkResponseDTO;
import com.schoolar.lynx.service.CompanySocialNetworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanySocialNetworkController {

    private final CompanySocialNetworkService service;
    @GetMapping("/{companyId}/social")
    public ResponseEntity<List<CompanySocialNetworkResponseDTO>> findAll(
            @PathVariable UUID companyId) {
        return ResponseEntity.ok(service.findAllSocialNetworksByCompany(companyId));
    }
}
