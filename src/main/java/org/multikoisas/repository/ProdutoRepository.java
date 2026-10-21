package org.multikoisas.repository;

import java.util.List;
import java.util.Optional;

import org.multikoisas.entities.ProdutoEntity;

public interface ProdutoRepository {
    List<ProdutoEntity> findAll();
    Optional<ProdutoEntity> findById(Integer id);
    ProdutoEntity save(ProdutoEntity entity);
    boolean update(Integer id, ProdutoEntity entity);
    boolean deleteById(Integer id);
}
