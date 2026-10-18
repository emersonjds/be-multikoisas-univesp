package org.multikoisas.service;

import org.multikoisas.dto.CategoriaProdutoRequest;
import org.multikoisas.dto.CategoriaProdutoResponse;

import java.util.List;

public interface CategoriaProdutoService {

    List<CategoriaProdutoResponse> findAll();

    List<CategoriaProdutoResponse> findByProdutoId(Integer produtoId);

    List<CategoriaProdutoResponse> findByCategoriaId(Integer categoriaId);

    CategoriaProdutoResponse save(CategoriaProdutoRequest categoriaProdutoRequest);

    boolean delete(Integer categoriaId, Integer produtoId);

}
