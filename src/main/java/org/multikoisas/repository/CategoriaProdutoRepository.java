package org.multikoisas.repository;

import org.multikoisas.entities.CategoriaProdutoEntity;

import java.util.List;

public interface CategoriaProdutoRepository {

    List<CategoriaProdutoEntity> findAll();

    List<CategoriaProdutoEntity> findByProdutoId(Integer produtoId);

    List<CategoriaProdutoEntity> findByCategoriaId(Integer categoriaId);

    CategoriaProdutoEntity save(CategoriaProdutoEntity entity);

    int delete(Integer produtoId, Integer categoriaId);

    boolean exists(Integer produtoId, Integer categoriaId);
}
