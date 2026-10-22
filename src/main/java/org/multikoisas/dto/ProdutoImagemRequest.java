package org.multikoisas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ProdutoImagemRequest {

    @NotNull(message = "O arquivo de imagem é obrigatório.")
    private MultipartFile file;
    @NotNull(message = "O produtoId e obrigatorio")
    private Integer produtoId;
}
