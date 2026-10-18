package org.multikoisas.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaProdutoResponse {

    private Integer produtoId;
    private Integer categoriaId;

}
