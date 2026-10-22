package org.multikoisas.repository;

import org.multikoisas.entities.CanalVendaProdutoEntity;

import java.util.List;

public interface CanalVendaProdutoRepository {

    List<CanalVendaProdutoEntity> findAll();

    List<CanalVendaProdutoEntity> findByProdutoId(Integer id);

    List<CanalVendaProdutoEntity> findByCanalVendaId(Integer id);

    CanalVendaProdutoEntity save(CanalVendaProdutoEntity entity);

    int delete(Integer produtoId, Integer canalVendaId);

    boolean exists(Integer produtoId, Integer canalVendaId);
}
