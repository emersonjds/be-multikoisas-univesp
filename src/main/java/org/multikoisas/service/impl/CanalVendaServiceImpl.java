package org.multikoisas.service.impl;

import org.multikoisas.dto.CanalVendaResponse;
import org.multikoisas.entities.CanalVendaEntity;
import org.multikoisas.repository.CanalVendaRepository;
import org.multikoisas.service.CanalVendaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CanalVendaServiceImpl implements CanalVendaService {

    private final CanalVendaRepository canalVendaRepository;

    public CanalVendaServiceImpl(CanalVendaRepository canalVendaRepository) {
        this.canalVendaRepository = canalVendaRepository;
    }

    private CanalVendaResponse toResponse(
            CanalVendaEntity canalVendaEntity) {

        return CanalVendaResponse.builder()
                .id(canalVendaEntity.getId())
                .nome(canalVendaEntity.getNome())
                .build();
    }

    @Override
    public List<CanalVendaResponse> findAll() {
        return canalVendaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }
}
