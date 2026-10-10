package org.multikoisas.service;

import org.multikoisas.dto.UserRequest;
import org.multikoisas.dto.UserResponse;

import java.util.List;

public interface UserService {

    public List<UserResponse> findAll();

    public UserRequest findById(Integer id);

    public UserResponse findByName(String name);

}
