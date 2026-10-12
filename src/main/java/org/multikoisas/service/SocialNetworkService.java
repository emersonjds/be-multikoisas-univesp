package org.multikoisas.service;

import org.multikoisas.dto.SocialNetworkResponse;

import java.util.List;

public interface SocialNetworkService {

    public List<SocialNetworkResponse> findAll();
}
