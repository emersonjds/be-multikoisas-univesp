package org.multikoisas.service;

import org.multikoisas.dto.CanalVendaProdutoResponse;

import java.util.List;

public interface CanalVendaProdutoService {

    public List<CanalVendaProdutoResponse> findAll();
}
