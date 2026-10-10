package org.multikoisas.service.impl;

import org.multikoisas.dto.UserRequest;
import org.multikoisas.dto.UserResponse;
import org.multikoisas.entities.UserEntity;
import org.multikoisas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.multikoisas.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private UserEntity userEntity;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserResponse toResponse(UserEntity userEntity) {
        return UserResponse.builder()
                .email(userEntity.getEmail())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .username(userEntity.getUsername())
                .build();
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }
    
    @Override
    public UserRequest findById(Integer id) {
        return userRepository.findById(id).stream().map(userEntity -> UserRequest.builder()
                .email(userEntity.getEmail())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .username(userEntity.getUsername())
                .build()).findFirst()
                .orElse(null);
    }

    @Override
    public UserResponse findByName(String name){
        return userRepository.findAll().stream()
                .filter(user -> user.getFirstName().equals(name))
                .findFirst()
                .map(this::toResponse)
                .orElse(null);
    }
}
