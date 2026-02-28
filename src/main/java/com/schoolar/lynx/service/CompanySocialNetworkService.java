package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.dto.CompanySocialNetworkRequestDTO;
import com.schoolar.lynx.domain.dto.CompanySocialNetworkResponseDTO;
import com.schoolar.lynx.domain.model.Company;
import com.schoolar.lynx.domain.model.CompanySocialNetwork;
import com.schoolar.lynx.domain.model.SocialNetwork;
import com.schoolar.lynx.domain.model.User;
import com.schoolar.lynx.repository.CompanyRepository;
import com.schoolar.lynx.repository.CompanySocialNetworkRepository;
import com.schoolar.lynx.repository.SocialNetworkRepository;
import com.schoolar.lynx.security.AuthenticatedUserService;
import com.schoolar.lynx.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanySocialNetworkService {
    private final CompanyRepository companyRepository;
    private final SocialNetworkRepository socialNetworkRepository;
    private final CompanySocialNetworkRepository companySocialNetworkRepository;
    private final AuthenticatedUserService authenticatedUserService;

    @Transactional
    public CompanySocialNetworkResponseDTO addCompanySocialNetwork(
            UUID companyId,
            CompanySocialNetworkRequestDTO dto) {

        User loggedUser = authenticatedUserService.get();

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Empresa não encontrada"
                ));

        if (!company.getPrincipalTeacher().getId()
                .equals(loggedUser.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Sem permissão"
            );
        }

        if (companySocialNetworkRepository
                .existsByCompanyIdAndSocialNetworkId(
                        companyId,
                        dto.getSocialNetworkId())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Rede já vinculada"
            );
        }

        SocialNetwork socialNetwork = socialNetworkRepository
                .findById(dto.getSocialNetworkId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Rede social não encontrada"
                ));

        CompanySocialNetwork entity = new CompanySocialNetwork();
        entity.setCompany(company);
        entity.setSocialNetwork(socialNetwork);
        entity.setUrl(dto.getUrl());

        CompanySocialNetwork finalDto = companySocialNetworkRepository.save(entity);

        return MapperUtil.parseObject(finalDto, CompanySocialNetworkResponseDTO.class) ;
    }


    public List<CompanySocialNetworkResponseDTO> findAllSocialNetworksByCompany (UUID companyId){
        User loggedUser = authenticatedUserService.get();

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Empresa não encontrada"
                ));
        List<CompanySocialNetwork> socialNetworks =
                companySocialNetworkRepository.findByCompanyId(companyId);

        return socialNetworks.stream()
                .map(entity -> {
                    CompanySocialNetworkResponseDTO dto =
                            new CompanySocialNetworkResponseDTO();

                    dto.setId(entity.getId());
                    dto.setSocialNetworkId(entity.getSocialNetwork().getId());
                    dto.setName(entity.getSocialNetwork().getName());
                    dto.setIcon(entity.getSocialNetwork().getIcon());
                    dto.setUrl(entity.getUrl());

                    return dto;
                })
                .toList();
    }
}
