package org.multikoisas.service;

import org.multikoisas.dto.UserRequest;
import org.multikoisas.dto.UserResponse;

import java.util.List;

public interface UserService {

    public List<UserResponse> findAll();

    public UserResponse findById(Integer id);

    public UserResponse findByUserName(String userName);

    public UserResponse save(UserRequest request);

    public UserResponse delete(String userName);

    public UserResponse update(String userName, UserRequest request);

}
