package org.multikoisas.service.impl;


import lombok.RequiredArgsConstructor;
import org.multikoisas.dto.UserRequest;
import org.multikoisas.dto.UserResponse;
import org.multikoisas.entities.UserEntity;
import org.multikoisas.service.UserService;
import org.springframework.stereotype.Service;
import org.multikoisas.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private UserResponse toResponse(UserEntity userEntity) {
        return UserResponse.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .userName(userEntity.getUserName())
                .build();
    }

    private UserEntity toEntity(UserRequest request) {
        return UserEntity.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .userName(request.getUserName())
                .build();
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }
    
    @Override
    public UserResponse findById(Integer id) {
        return userRepository.findById(id).stream().map(this::toResponse).findFirst().orElse(null);
    }

//    @Override
//    public UserResponse save(UserRequest request) {
//
//        // Se o retorno do banco estiver presente, a exceção é lançada imediatamente
//        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
//            throw new IllegalArgumentException("Já existe um usuário cadastrado com este e-mail.");
//        });
//
//        userRepository.findByUsername(request.getUserName()).ifPresent(user -> {
//            throw new IllegalArgumentException("Este nome de usuário já está em uso.");
//        });
//
//        // Caminho livre e seguro para salvar
//        UserEntity entityToSave = toEntity(request);
//        UserEntity savedEntity = userRepository.save(entityToSave);
//
//        return toResponse(savedEntity);
//
//    }
//
//    @Override
//    public UserResponse delete(String userName) {
//        return toResponse(userRepository.delete(userName));
//    }
//
}
