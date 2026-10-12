package org.multikoisas.service.impl;

import org.multikoisas.dto.SocialNetworkResponse;
import org.multikoisas.entities.SocialNetworkEntity;
import org.multikoisas.repository.SocialNetworkRepository;
import org.multikoisas.service.SocialNetworkService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocialNetworkServiceImpl implements SocialNetworkService {

    private SocialNetworkRepository socialNetworkRepository;

    public SocialNetworkServiceImpl(SocialNetworkRepository socialNetworkRepository) {
        this.socialNetworkRepository = socialNetworkRepository;
    }

    private SocialNetworkResponse toResponse(SocialNetworkEntity socialNetworkEntity)
    {
        return SocialNetworkResponse.builder()
                .id(socialNetworkEntity.getId())
                .descricao(socialNetworkEntity.getDescricao())
                .link(socialNetworkEntity.getLink())
                .build();

    }

    @Override
    public List<SocialNetworkResponse> findAll() {
        return socialNetworkRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

}
