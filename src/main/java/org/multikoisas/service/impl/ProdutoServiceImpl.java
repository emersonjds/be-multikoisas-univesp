
package org.multikoisas.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.multikoisas.dto.ProdutoRequest;
import org.multikoisas.dto.ProdutoResponse;
import org.multikoisas.entities.ProdutoEntity;
import org.multikoisas.repository.ProdutoRepository;
import org.multikoisas.service.ProdutoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    private ProdutoResponse toResponse(ProdutoEntity entity) {
        return ProdutoResponse.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .descricaoProduto(entity.getDescricaoProduto())
                .build();
    }

    private void validar(ProdutoRequest request) {
        if (request == null
                || request.getNome() == null
                || request.getNome().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O nome do produto é obrigatório");
        }
    }

    @Override
    public List<ProdutoResponse> findAll() {
        return produtoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ProdutoResponse findById(Integer id) {
        ProdutoEntity entity = produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Produto não encontrado"));

        return toResponse(entity);
    }

    @Override
    public ProdutoResponse save(ProdutoRequest request) {
        validar(request);

        ProdutoEntity entity = new ProdutoEntity();
        entity.setNome(request.getNome());
        entity.setDescricaoProduto(request.getDescricaoProduto());

        return toResponse(produtoRepository.save(entity));
    }

    @Override
    public boolean update(Integer id, ProdutoRequest request) {
        validar(request);

        if (produtoRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Produto não encontrado");
        }

        ProdutoEntity entity = new ProdutoEntity();
        entity.setId(id);
        entity.setNome(request.getNome());
        entity.setDescricaoProduto(request.getDescricaoProduto());

        return produtoRepository.update(id, entity);
    }

    @Override
    public boolean deleteById(Integer id) {
        if (produtoRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Produto não encontrado");
        }

        try {
            return produtoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Não é possível excluir este produto porque ele está associado a outras informações.");
        }
    }
}
