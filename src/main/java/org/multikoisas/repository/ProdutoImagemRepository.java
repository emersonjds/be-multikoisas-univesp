package org.multikoisas.repository;

import org.multikoisas.entities.ProdutoImagemEntity;

import java.util.List;

public interface ProdutoImagemRepository {

    List<ProdutoImagemEntity> findAll();

    ProdutoImagemEntity findById(Integer productId);

    List<ProdutoImagemEntity> findByProduct(Integer productId);

    ProdutoImagemEntity save(ProdutoImagemEntity entity);

    boolean deleteByProduct(ProdutoImagemEntity entity);
}
