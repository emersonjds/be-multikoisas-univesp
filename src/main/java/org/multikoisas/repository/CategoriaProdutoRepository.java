package org.multikoisas.repository;

import org.multikoisas.entities.CategoriaProdutoEntity;

import java.util.List;

public interface CategoriaProdutoRepository {

    List<CategoriaProdutoEntity> findAll();

}
