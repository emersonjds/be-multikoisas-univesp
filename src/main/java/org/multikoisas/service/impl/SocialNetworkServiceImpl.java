package org.multikoisas.service.impl;

import lombok.RequiredArgsConstructor;
import org.multikoisas.dto.SocialNetworkRequest;
import org.multikoisas.dto.SocialNetworkResponse;
import org.multikoisas.dto.UserRequest;
import org.multikoisas.dto.UserResponse;
import org.multikoisas.entities.SocialNetworkEntity;
import org.multikoisas.entities.UserEntity;
import org.multikoisas.repository.SocialNetworkRepository;
import org.multikoisas.service.SocialNetworkService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor

public class SocialNetworkServiceImpl implements SocialNetworkService {

        private final SocialNetworkRepository socialRepository;

    public List<SocialNetworkResponse> findAll() {
        return socialRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public SocialNetworkResponse findByName(String name) {
        return socialRepository.findByName(name).stream().map(this::toResponse).findFirst().orElse(null);
    }

    @Override
    public SocialNetworkResponse save(SocialNetworkRequest request) {

        socialRepository.findByName(request.getDescricao()).ifPresent(user -> {
            throw new IllegalArgumentException("Esta rede social já está cadastrada.");
        });

        SocialNetworkEntity entityToSave = toEntity(request);
        SocialNetworkEntity savedEntity = socialRepository.save(entityToSave);

        return toResponse(savedEntity);
    }

    @Override
    public SocialNetworkResponse delete(String name) {

        SocialNetworkEntity entityDeleted = socialRepository.delete(name);

        if (entityDeleted == null) {
            return null;
        }

        return toResponse(entityDeleted);
    }

    @Override
    public SocialNetworkResponse update(String descricao, SocialNetworkRequest request) {

        SocialNetworkEntity socialNetworkToUpdate = socialRepository.findByName(descricao)
                .orElseThrow(() -> new IllegalArgumentException("Descrição não encontrado.")); // 1. Lança erro se não achar

        // 2. A Mágica Funcional: Mescla limpa e direta
        updateIfValid(request.getDescricao(), socialNetworkToUpdate::setDescricao);
        updateIfValid(request.getLink(), socialNetworkToUpdate::setLink);

        // 3. Regra de Negócio Isolada (O E-mail)
        // O e-mail continua precisando de um bloco próprio porque requer validação no banco de dados.
        String novaDescricao = request.getDescricao();
        if (novaDescricao != null && !novaDescricao.isBlank() && !novaDescricao.equals(socialNetworkToUpdate.getDescricao())) {

            socialRepository.findByName(novaDescricao).ifPresent(existingSocialNetwork -> {
                throw new IllegalArgumentException("Esta rede social já está cadastrada.");
            });

            socialNetworkToUpdate.setDescricao(novaDescricao);
        }

        // 4. Gravação e Retorno
        socialRepository.update(socialNetworkToUpdate);
        return toResponse(socialNetworkToUpdate);

    }

    private SocialNetworkResponse toResponse(SocialNetworkEntity socialNetworkEntity)
    {
        return SocialNetworkResponse.builder()
                .id(socialNetworkEntity.getId())
                .descricao(socialNetworkEntity.getDescricao())
                .link(socialNetworkEntity.getLink())
                .build();

    }

    private SocialNetworkEntity toEntity(SocialNetworkRequest socialNetworkRequest)
    {
        return SocialNetworkEntity.builder()
                .descricao(socialNetworkRequest.getDescricao())
                .link(socialNetworkRequest.getLink())
                .build();
    }

    private void updateIfValid(String novoValor, Consumer<String> atualizador) {
        if (novoValor != null && !novoValor.isBlank()) {
            atualizador.accept(novoValor);
        }
    }

}
