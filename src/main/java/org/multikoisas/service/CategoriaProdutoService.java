package org.multikoisas.service;

import org.multikoisas.dto.CategoriaProdutoResponse;

import java.util.List;

public interface CategoriaProdutoService {

    public List<CategoriaProdutoResponse> findAll();
}
