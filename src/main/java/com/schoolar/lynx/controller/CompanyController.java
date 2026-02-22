package com.schoolar.lynx.controller;

import com.schoolar.lynx.domain.dto.CompanyResponseDTO;
import com.schoolar.lynx.domain.dto.RegisterCompanyDTO;
import com.schoolar.lynx.domain.dto.UserResponseDTO;
import com.schoolar.lynx.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {
    @Autowired
    private final CompanyService service;

    @PostMapping
    public CompanyResponseDTO createCompany(@RequestBody RegisterCompanyDTO dto){
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public CompanyResponseDTO findCompanyById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCompanyById(@PathVariable UUID id){
        service.deleteById(id);
    }

//    @PutMapping("/{id}")
//    public CompanyResponseDTO updateCompanyById(@PathVariable UUID id){
//        return new CompanyResponseDTO();
//    }
}
