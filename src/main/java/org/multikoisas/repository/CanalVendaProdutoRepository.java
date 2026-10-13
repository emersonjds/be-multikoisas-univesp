package org.multikoisas.repository;

import org.multikoisas.entities.CanalVendaProdutoEntity;

import java.util.List;

public interface CanalVendaProdutoRepository {

    List<CanalVendaProdutoEntity> findAll();

}
