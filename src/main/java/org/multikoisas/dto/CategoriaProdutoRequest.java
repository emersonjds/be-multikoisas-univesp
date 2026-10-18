package org.multikoisas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaProdutoRequest {

    @NotNull(message = "categoriaId é obrigatório")
    private Integer categoriaId;

    @NotNull(message = "produtoId é obrigatório")
    private Integer produtoId;
}
