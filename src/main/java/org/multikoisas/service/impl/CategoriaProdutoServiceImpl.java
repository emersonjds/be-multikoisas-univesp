package org.multikoisas.service.impl;

import lombok.RequiredArgsConstructor;
import org.multikoisas.dto.CategoriaProdutoRequest;
import org.multikoisas.dto.CategoriaProdutoResponse;
import org.multikoisas.entities.CategoriaProdutoEntity;
import org.multikoisas.repository.CategoriaProdutoRepository;
import org.multikoisas.service.CategoriaProdutoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CategoriaProdutoServiceImpl implements CategoriaProdutoService {

    private final CategoriaProdutoRepository repository;

    @Override
    public List<CategoriaProdutoResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<CategoriaProdutoResponse> findByProdutoId(Integer produtoId) {
        return repository.findByProdutoId(produtoId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<CategoriaProdutoResponse> findByCategoriaId(Integer categoriaId) {
        return repository.findByCategoriaId(categoriaId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CategoriaProdutoResponse save(CategoriaProdutoRequest request) {
        if (repository.exists(request.getProdutoId(), request.getCategoriaId())) {
            throw new IllegalArgumentException("Este produto já está associado a esta categoria.");
        }

        CategoriaProdutoEntity entity = new CategoriaProdutoEntity();
        entity.setProdutoId(request.getProdutoId());
        entity.setCategoriaId(request.getCategoriaId());

        try {
            repository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Produto ou categoria inexistente. Verifique os IDs informados.");
        }

        return toResponse(entity);
    }

    @Override
    public boolean delete(Integer produtoId, Integer categoriaId) {
        // 1. Apaga e recolhe a quantidade de linhas deletadas
        int linhasAfetadas = repository.delete(produtoId, categoriaId);

        // 2. Se nenhuma linha foi apagada, o vínculo não existia
        if (linhasAfetadas == 0) {
            throw new IllegalArgumentException("O vínculo entre este produto e categoria não existe.");
        }
        return true;
    }

    private CategoriaProdutoResponse toResponse(CategoriaProdutoEntity entity) {
        return CategoriaProdutoResponse.builder()
                .categoriaId(entity.getCategoriaId())
                .produtoId(entity.getProdutoId())
                .build();
    }
}
