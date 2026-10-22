package org.multikoisas.service.impl;

import lombok.RequiredArgsConstructor;
import org.multikoisas.dto.CanalVendaProdutoRequest;
import org.multikoisas.dto.CanalVendaProdutoResponse;
import org.multikoisas.entities.CanalVendaProdutoEntity;
import org.multikoisas.repository.CanalVendaProdutoRepository;
import org.multikoisas.service.CanalVendaProdutoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CanalVendaProdutoServiceImpl implements CanalVendaProdutoService {

    private final CanalVendaProdutoRepository canalVendaProdutoRepository;

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

    @Override
    public List<CanalVendaProdutoResponse> findByProdutoId(Integer produtoId) {
        return canalVendaProdutoRepository.findByProdutoId(produtoId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<CanalVendaProdutoResponse> findByCanalVendaId(Integer canalVendaId) {
        return canalVendaProdutoRepository.findByCanalVendaId(canalVendaId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CanalVendaProdutoResponse save(CanalVendaProdutoRequest request) {

        if (canalVendaProdutoRepository.exists(
                request.getProdutoId(),
                request.getCanalVendaId())) {

            throw new IllegalArgumentException(
                    "Este produto já está associado a este canal de venda."
            );
        }

        CanalVendaProdutoEntity entity = new CanalVendaProdutoEntity();

        entity.setProdutoId(request.getProdutoId());
        entity.setCanalVendaId(request.getCanalVendaId());
        entity.setLink(request.getLink());

        try {
            canalVendaProdutoRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(
                    "Produto ou canal de venda inexistente. Verifique os IDs informados."
            );
        }

        return toResponse(entity);
    }

    @Override
    public boolean delete(Integer produtoId, Integer canalVendaId) {

        int linhasAfetadas =
                canalVendaProdutoRepository.delete(produtoId, canalVendaId);

        if (linhasAfetadas == 0) {
            throw new IllegalArgumentException(
                    "O vínculo entre este produto e canal de venda não existe."
            );
        }

        return true;
    }
}
