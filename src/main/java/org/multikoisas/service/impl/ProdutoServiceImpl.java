package org.multikoisas.service.impl;

import org.multikoisas.dto.ProdutoResponse;
import org.multikoisas.entities.ProdutoEntity;
import org.multikoisas.repository.ProdutoRepository;
import org.multikoisas.service.ProdutoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    private ProdutoResponse toResponse(ProdutoEntity produtoEntity) {
        return ProdutoResponse.builder()
                .id(produtoEntity.getId())
                .nome(produtoEntity.getNome())
                .descricaoProduto(produtoEntity.getDescricaoProduto())
                .build();
    }

    @Override
    public List<ProdutoResponse> findAll() {
        return produtoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }
}
