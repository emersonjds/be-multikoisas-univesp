
package org.multikoisas.service.impl;

import java.util.List;

import org.multikoisas.dto.CategoriaRequest;
import org.multikoisas.dto.CategoriaResponse;
import org.multikoisas.entities.CategoriaEntity;
import org.multikoisas.repository.CategoriaRepository;
import org.multikoisas.service.CategoriaService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    private CategoriaResponse toResponse(CategoriaEntity entity) {
        return CategoriaResponse.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .build();
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O nome da categoria é obrigatório"
            );
        }
    }

    @Override
    public List<CategoriaResponse> findAll() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CategoriaResponse findById(Integer id) {
        CategoriaEntity entity = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoria não encontrada"
                ));

        return toResponse(entity);
    }

    @Override
    public CategoriaResponse save(CategoriaRequest request) {
        validarNome(request.getNome());

        CategoriaEntity entity = new CategoriaEntity();
        entity.setNome(request.getNome());

        CategoriaEntity salva = categoriaRepository.save(entity);

        return toResponse(salva);
    }

    @Override
    public boolean update(Integer id, CategoriaRequest request) {
        validarNome(request.getNome());

        if (categoriaRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Categoria não encontrada"
            );
        }

        CategoriaEntity entity = new CategoriaEntity();
        entity.setId(id);
        entity.setNome(request.getNome());

        return categoriaRepository.update(id, entity);
    }

    @Override
    public boolean deleteById(Integer id) {
        if (categoriaRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Categoria não encontrada"
            );
        }

        try {
            return categoriaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Não é possível excluir esta categoria porque ela está associada a um ou mais produtos."
            );
        }
    }
}
