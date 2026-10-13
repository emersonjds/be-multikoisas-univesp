package org.multikoisas.service.impl;

import org.multikoisas.dto.CategoriaProdutoResponse;
import org.multikoisas.entities.CategoriaProdutoEntity;
import org.multikoisas.repository.CategoriaProdutoRepository;
import org.multikoisas.service.CategoriaProdutoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaProdutoServiceImpl implements CategoriaProdutoService {

    private final CategoriaProdutoRepository categoriaProdutoRepository;

    public CategoriaProdutoServiceImpl(
            CategoriaProdutoRepository categoriaProdutoRepository) {
        this.categoriaProdutoRepository = categoriaProdutoRepository;
    }

    private CategoriaProdutoResponse toResponse(
            CategoriaProdutoEntity categoriaProdutoEntity) {

        return CategoriaProdutoResponse.builder()
                .categoriaId(categoriaProdutoEntity.getCategoriaId())
                .produtoId(categoriaProdutoEntity.getProdutoId())
                .build();
    }

    @Override
    public List<CategoriaProdutoResponse> findAll() {
        return categoriaProdutoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }
}
