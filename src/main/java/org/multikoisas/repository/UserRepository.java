package org.multikoisas.repository;

import org.multikoisas.entities.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface UserRepository {

    List< UserEntity> findAll();

    Optional<UserEntity> findById(Integer id);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    UserEntity save(UserEntity entity);

    UserEntity delete(String username);

    void update(UserEntity entity);

}
