package org.multikoisas.service.impl;

import lombok.RequiredArgsConstructor;
import org.multikoisas.dto.ProdutoImagemRequest;
import org.multikoisas.dto.ProdutoImagemResponse;
import org.multikoisas.entities.ProdutoImagemEntity;
import org.multikoisas.repository.ProdutoImagemRepository;
import org.multikoisas.service.ProdutoImagemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProdutoImagemServiceImpl implements ProdutoImagemService {

    private final ProdutoImagemRepository repository;
    private final S3StorageService service; // Injeção da Interface, não da implementação S3!

    @Override
    @Transactional // Garante que se o banco falhar, o processo inteiro aborta
    public ProdutoImagemResponse save(ProdutoImagemRequest request) {

        // 1. Envia para a nuvem e recebe a URL (Responsabilidade do StorageService)
        String publicUrl = service.uploadFile(request.getFile());

        // 2. Constrói a Entidade com os dados do Request + URL da nuvem
        ProdutoImagemEntity entity = new ProdutoImagemEntity();
        entity.setProdutoId(request.getProdutoId());
        entity.setImagemUrl(publicUrl);

        // 3. Salva no banco de dados (Responsabilidade do Repository)
        ProdutoImagemEntity savedEntity = repository.save(entity);

        // 4. Converte e devolve
        return toResponse(savedEntity);
    }

    public ProdutoImagemResponse toResponse(ProdutoImagemEntity entity) {
        return ProdutoImagemResponse.builder()
                .id(entity.getId())
                .produtoId(entity.getProdutoId())
                .imagemUrl(entity.getImagemUrl())
                .build();

    }

}
