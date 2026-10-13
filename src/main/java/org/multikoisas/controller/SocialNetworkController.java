package org.multikoisas.controller;

import org.multikoisas.dto.SocialNetworkResponse;
import org.multikoisas.service.impl.SocialNetworkServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/socialnetwork")

public class SocialNetworkController {

    private SocialNetworkServiceImpl socialNetworkServiceImpl;

    public SocialNetworkController(SocialNetworkServiceImpl socialNetworkServiceImpl)
    {
        this.socialNetworkServiceImpl = socialNetworkServiceImpl;
    };

    @GetMapping
    public ResponseEntity<List<SocialNetworkResponse>> getSocialNetwork()
    {
        List<SocialNetworkResponse> socialNetworks = socialNetworkServiceImpl.findAll();
        return ResponseEntity.ok(socialNetworks);
    }
}
