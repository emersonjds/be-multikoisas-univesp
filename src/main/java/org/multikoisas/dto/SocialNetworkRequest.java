package org.multikoisas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class SocialNetworkRequest
{
    private String descricao;
    @NotBlank(message = "O link é obrigatório")
    private String link;
}
