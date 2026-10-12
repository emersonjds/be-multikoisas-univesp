package org.multikoisas.repository;

import org.multikoisas.entities.ProdutoEntity;

import java.util.List;

public interface ProdutoRepository {

    List<ProdutoEntity> findAll();

}
