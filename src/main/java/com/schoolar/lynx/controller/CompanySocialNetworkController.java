package com.schoolar.lynx.controller;

import com.schoolar.lynx.domain.dto.CompanySocialNetworkRequestDTO;
import com.schoolar.lynx.domain.dto.CompanySocialNetworkResponseDTO;
import com.schoolar.lynx.service.CompanySocialNetworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{companyId}/social")
    public ResponseEntity<CompanySocialNetworkResponseDTO> addToCompany(
            @PathVariable UUID companyId,
            @RequestBody CompanySocialNetworkRequestDTO dto){
        return ResponseEntity.ok(service.addCompanySocialNetwork(companyId, dto));
    }
}
