package org.multikoisas.repository;

import org.multikoisas.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface UserRepository {

    List< UserEntity> findAll();

    Optional<UserEntity> findById(Integer id);

    UserEntity save(String email);

    UserEntity delete(Integer id);

}
