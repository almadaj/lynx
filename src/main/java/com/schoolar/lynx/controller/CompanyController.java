package com.schoolar.lynx.controller;

import com.schoolar.lynx.domain.dto.*;
import com.schoolar.lynx.domain.enums.Role;
import com.schoolar.lynx.service.CompanyService;
import com.schoolar.lynx.service.UserCompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {
    @Autowired
    private final CompanyService service;
    private final UserCompanyService userCompanyService;

    @PostMapping
    public ResponseEntity<CompanyResponseDTO> createCompany(@Valid @RequestBody RegisterCompanyDTO dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteCompanyById(@PathVariable UUID id){
        service.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponseDTO> updateCompanyById(@PathVariable UUID id, @Valid @RequestBody UpdateCompanyDTO dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/{id}/teachers")
    public ResponseEntity<List<UserResponseDTO>> getAllTeachersByCompany(@PathVariable UUID id){
        return ResponseEntity.ok(service.getTeachersBySchoolId(id));
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<UserResponseDTO>> getAllStudentsByCompany(@PathVariable UUID id){
        return ResponseEntity.ok(service.getStudentsBySchoolId(id));
    }

    @PostMapping("/{id}/students")
    public ResponseEntity<UserCompanyResponse> addStudentToCompany(@PathVariable UUID id, @RequestBody String email){
        return ResponseEntity.ok(userCompanyService.addStudentToCompany(id, email));
    }

    @PostMapping("/{id}/teachers")
    public ResponseEntity<UserCompanyResponse> addTeacherToCompany(@PathVariable UUID id, @RequestBody AddNewMemberDTO dto){
        return ResponseEntity.ok(userCompanyService.addTeacherToCompany(id, dto));
    }

    @PutMapping("/{id}/promote")
    public ResponseEntity<UserCompanyResponse> promoteTeacher(@PathVariable UUID id, @RequestBody AddNewMemberDTO dto){
        return ResponseEntity.ok(userCompanyService.promoteToNewRole(id, dto));
    }
}
