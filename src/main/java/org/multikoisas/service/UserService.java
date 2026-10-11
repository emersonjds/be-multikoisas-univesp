package org.multikoisas.service;

import org.multikoisas.dto.UserRequest;
import org.multikoisas.dto.UserResponse;
import org.multikoisas.entities.UserEntity;

import java.util.List;

public interface UserService {

    public List<UserResponse> findAll();

    public UserResponse findById(Integer id);
//
//    public UserResponse save(UserRequest request);
//
//    public UserResponse delete(String userName);

}
