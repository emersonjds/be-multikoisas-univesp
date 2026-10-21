
package org.multikoisas.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.multikoisas.dto.CanalVendaRequest;
import org.multikoisas.dto.CanalVendaResponse;
import org.multikoisas.entities.CanalVendaEntity;
import org.multikoisas.repository.CanalVendaRepository;
import org.multikoisas.service.CanalVendaService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CanalVendaServiceImpl implements CanalVendaService {

    private final CanalVendaRepository canalVendaRepository;

    private CanalVendaResponse toResponse(CanalVendaEntity entity) {
        return CanalVendaResponse.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .build();
    }

    @Override
    public List<CanalVendaResponse> findAll() {
        return canalVendaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CanalVendaResponse findById(Integer id) {
        CanalVendaEntity entity = canalVendaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Canal de venda não encontrado"));

        return toResponse(entity);
    }

    @Override
    public CanalVendaResponse save(CanalVendaRequest request) {
        if (request.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O ID é obrigatório");
        }

        if (request.getNome() == null || request.getNome().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O nome é obrigatório");
        }

        if (canalVendaRepository.findById(request.getId()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe um canal com esse ID");
        }

        CanalVendaEntity entity = new CanalVendaEntity();
        entity.setId(request.getId());
        entity.setNome(request.getNome());

        return toResponse(canalVendaRepository.save(entity));
    }

    @Override
    public boolean update(Integer id, CanalVendaRequest request) {
        if (request.getNome() == null || request.getNome().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O nome é obrigatório");
        }

        if (canalVendaRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Canal de venda não encontrado");
        }

        CanalVendaEntity entity = new CanalVendaEntity();
        entity.setId(id);
        entity.setNome(request.getNome());

        return canalVendaRepository.update(id, entity);
    }

    @Override
    public boolean deleteById(Integer id) {
        if (canalVendaRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Canal de venda não encontrado");
        }

        return canalVendaRepository.deleteById(id);
    }
}
