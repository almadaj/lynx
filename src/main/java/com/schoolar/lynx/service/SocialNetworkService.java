package com.schoolar.lynx.service;

import com.schoolar.lynx.domain.dto.SocialNetworkResponseDTO;
import com.schoolar.lynx.domain.model.User;
import com.schoolar.lynx.repository.SocialNetworkRepository;
import com.schoolar.lynx.security.AuthenticatedUserService;
import com.schoolar.lynx.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class SocialNetworkService {
    private final SocialNetworkRepository repository;
    private final AuthenticatedUserService authenticatedUserService;

    public List<SocialNetworkResponseDTO> findAll(){
        User loggedUser = authenticatedUserService.get();
        var listSocials = repository.findAll();
        return MapperUtil.parseListObjects(listSocials, SocialNetworkResponseDTO.class);
    }
}
