package org.multikoisas.service;

import org.multikoisas.dto.CategoriaResponse;

import java.util.List;

public interface CategoriaService {

    public List<CategoriaResponse> findAll();
}
