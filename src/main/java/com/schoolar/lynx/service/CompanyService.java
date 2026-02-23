package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.dto.CompanyResponseDTO;
import com.schoolar.lynx.domain.dto.RegisterCompanyDTO;
import com.schoolar.lynx.domain.model.Company;
import com.schoolar.lynx.domain.model.User;
import com.schoolar.lynx.repository.CompanyRepository;
import com.schoolar.lynx.repository.UserRepository;
import com.schoolar.lynx.security.UserDetailsImpl;
import com.schoolar.lynx.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    //TODO: Validação do CNPJ
    //TODO: Validação de duplicatas
    public CompanyResponseDTO create(RegisterCompanyDTO dto) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        UserDetailsImpl userDetails =
                (UserDetailsImpl) authentication.getPrincipal();

        String username = userDetails.getUsername();

        User loggedUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));

        User principal = userRepository.findById(loggedUser.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Professor não encontrado"
                ));

        if (!loggedUser.isAdmin()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Estudantes não podem criar instituições"
            );
        }

        Company company = MapperUtil.parseObject(dto, Company.class);
        company.setPrincipalTeacher(principal);
        Company savedCompany = companyRepository.save(company);
        return MapperUtil.parseObject(savedCompany, CompanyResponseDTO.class);
    }

    public CompanyResponseDTO findById(UUID id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Empresa não encontrada"
                ));

        if (!company.isActive()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Empresa está inativa"
            );
        }

        CompanyResponseDTO dto = new CompanyResponseDTO();
        dto.setId(company.getId());
        dto.setPublicName(company.getPublicName());
        dto.setCompanyName(company.getCompanyName());
        dto.setEmail(company.getEmail());
        dto.setPhone(company.getPhone());
        dto.setCnpj(company.getCnpj());
        dto.setAddress(company.getAddress());
        dto.setHasOnlineClass(company.isHasOnlineClass());
        dto.setActive(company.isActive());

        if (company.getPrincipalTeacher() != null) {
            dto.setPrincipalTeacherId(company.getPrincipalTeacher().getId());
        }
        return dto;
    }

    public void deleteById(UUID id){
        Company company = companyRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Empresa não encontrada"
                ));
        company.setActive(false);
        companyRepository.save(company);
    }
}
