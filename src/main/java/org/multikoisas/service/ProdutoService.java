package org.multikoisas.service;

import org.multikoisas.dto.ProdutoResponse;

import java.util.List;

public interface ProdutoService {

    public List<ProdutoResponse> findAll();
}
