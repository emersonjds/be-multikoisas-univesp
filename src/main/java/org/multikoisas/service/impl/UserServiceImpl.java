package org.multikoisas.service.impl;


import lombok.RequiredArgsConstructor;
import org.multikoisas.dto.UserRequest;
import org.multikoisas.dto.UserResponse;
import org.multikoisas.entities.UserEntity;
import org.multikoisas.service.UserService;
import org.springframework.stereotype.Service;
import org.multikoisas.repository.UserRepository;

import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }
    
    @Override
    public UserResponse findById(Integer id) {
        return userRepository.findById(id).map(this::toResponse).orElse(null);
    }

    @Override
    public UserResponse findByUsername(String username) {
        return userRepository.findByUsername(username).map(this::toResponse).orElse(null);
    }

    @Override
    public UserResponse save(UserRequest request) {

        userRepository.findByUsername(request.getUsername()).ifPresent(user -> {
            throw new IllegalArgumentException("Este nome de usuário já está em uso.");
        });

        // Se o retorno do banco estiver presente, a exceção é lançada imediatamente
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este e-mail.");
        });

        // Caminho livre e seguro para salvar
        UserEntity entityToSave = toEntity(request);
        UserEntity savedEntity = userRepository.save(entityToSave);

        return toResponse(savedEntity);

    }

    @Override
    public UserResponse delete(String username) {

        UserEntity entityDeleted = userRepository.delete(username);

        if (entityDeleted == null) {
            return null;
        }

        return toResponse(entityDeleted);

    }

    @Override
    public UserResponse update(String username, UserRequest request) {

        UserEntity userToUpdate = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado.")); // 1. Lança erro se não achar

        // 2. A Mágica Funcional: Mescla limpa e direta
        updateIfValid(request.getFirstName(), userToUpdate::setFirstName);
        updateIfValid(request.getLastName(), userToUpdate::setLastName);
        updateIfValid(request.getPassword(), userToUpdate::setPassword);

        // 3. Regra de Negócio Isolada (O E-mail)
        // O e-mail continua precisando de um bloco próprio porque requer validação no banco de dados.
        String novoEmail = request.getEmail();
        if (novoEmail != null && !novoEmail.isBlank() && !novoEmail.equals(userToUpdate.getEmail())) {

            userRepository.findByEmail(novoEmail).ifPresent(existingUser -> {
                throw new IllegalArgumentException("Este e-mail já está em uso por outra conta.");
            });

            userToUpdate.setEmail(novoEmail);
        }

        // 4. Gravação e Retorno
        userRepository.update(userToUpdate);
        return toResponse(userToUpdate);

    }

    private UserResponse toResponse(UserEntity userEntity) {
        return UserResponse.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .username(userEntity.getUsername())
                .build();
    }

    private UserEntity toEntity(UserRequest request) {
        return UserEntity.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .build();
    }

    private void updateIfValid(String novoValor, Consumer<String> atualizador) {
        if (novoValor != null && !novoValor.isBlank()) {
            atualizador.accept(novoValor);
        }
    }
}
