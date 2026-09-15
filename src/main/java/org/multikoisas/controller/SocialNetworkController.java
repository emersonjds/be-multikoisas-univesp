package org.multikoisas.controller;

import org.multikoisas.dto.SocialNetworkResponse;
import org.multikoisas.service.impl.SocialNetworkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/socialnetwork")

public class SocialNetworkController {

    private SocialNetworkService socialNetworkService;

    public SocialNetworkController(SocialNetworkService socialNetworkService)
    {
        this.socialNetworkService = socialNetworkService;
    };

    @GetMapping
    public ResponseEntity<List<SocialNetworkResponse>> getSocialNetwork()
    {
        List<SocialNetworkResponse> socialNetworks = socialNetworkService.findAll();
        return ResponseEntity.ok(socialNetworks);
    }
}
