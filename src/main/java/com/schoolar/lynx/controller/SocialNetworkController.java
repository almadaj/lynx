package com.schoolar.lynx.controller;

import com.schoolar.lynx.domain.dto.SocialNetworkResponseDTO;
import com.schoolar.lynx.service.SocialNetworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/social-network")
@RequiredArgsConstructor
public class SocialNetworkController {
    @Autowired
    private final SocialNetworkService service;

    @GetMapping
    public List<SocialNetworkResponseDTO> listAll(){
        return service.findAll();
    }
}
