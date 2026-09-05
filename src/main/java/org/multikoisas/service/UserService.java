package org.multikoisas.service;

import org.multikoisas.dto.UserResponse;

import java.util.List;

public interface UserService {

    public List<UserResponse> findAll();

    public UserResponse findByName(String name);

}
