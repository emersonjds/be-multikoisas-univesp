package org.multikoisas.service.impl;

import lombok.RequiredArgsConstructor;
import org.multikoisas.dto.CategoriaResponse;
import org.multikoisas.entities.CategoriaEntity;
import org.multikoisas.repository.CategoriaRepository;
import org.multikoisas.service.CategoriaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    private CategoriaResponse toResponse(CategoriaEntity categoriaEntity) {
        return CategoriaResponse.builder()
                .id(categoriaEntity.getId())
                .nome(categoriaEntity.getNome())
                .build();
    }

    @Override
    public List<CategoriaResponse> findAll() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }
}
