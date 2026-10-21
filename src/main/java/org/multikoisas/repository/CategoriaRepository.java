package org.multikoisas.repository;

import java.util.List;
import java.util.Optional;

import org.multikoisas.entities.CategoriaEntity;

public interface CategoriaRepository {
    List<CategoriaEntity> findAll();
    Optional<CategoriaEntity> findById(Integer id);
    CategoriaEntity save(CategoriaEntity entity);
    boolean update(Integer id, CategoriaEntity entity);
    boolean deleteById(Integer id);
}