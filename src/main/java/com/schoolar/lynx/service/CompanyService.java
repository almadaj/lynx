package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.dto.CompanyResponseDTO;
import com.schoolar.lynx.domain.dto.RegisterCompanyDTO;
import com.schoolar.lynx.domain.model.Company;
import com.schoolar.lynx.domain.model.User;
import com.schoolar.lynx.repository.CompanyRepository;
import com.schoolar.lynx.repository.UserRepository;
import com.schoolar.lynx.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public CompanyResponseDTO create(RegisterCompanyDTO dto) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        User loggedUser = (User) authentication.getPrincipal();
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
        //TODO: Validação do CNPJ
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
        return MapperUtil.parseObject(company, CompanyResponseDTO.class);
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
