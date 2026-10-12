package org.multikoisas.service.impl;

import org.multikoisas.dto.CanalVendaProdutoResponse;
import org.multikoisas.entities.CanalVendaProdutoEntity;
import org.multikoisas.repository.CanalVendaProdutoRepository;
import org.multikoisas.service.CanalVendaProdutoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CanalVendaProdutoServiceImpl implements CanalVendaProdutoService {

    private final CanalVendaProdutoRepository canalVendaProdutoRepository;

    public CanalVendaProdutoServiceImpl(
            CanalVendaProdutoRepository canalVendaProdutoRepository) {
        this.canalVendaProdutoRepository = canalVendaProdutoRepository;
    }

    private CanalVendaProdutoResponse toResponse(
            CanalVendaProdutoEntity canalVendaProdutoEntity) {

        return CanalVendaProdutoResponse.builder()
                .produtoId(canalVendaProdutoEntity.getProdutoId())
                .canalVendaId(canalVendaProdutoEntity.getCanalVendaId())
                .link(canalVendaProdutoEntity.getLink())
                .build();
    }

    @Override
    public List<CanalVendaProdutoResponse> findAll() {
        return canalVendaProdutoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }
}
