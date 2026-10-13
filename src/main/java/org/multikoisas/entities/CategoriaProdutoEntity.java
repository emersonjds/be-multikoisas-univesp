package org.multikoisas.entities;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoriaProdutoEntity {

    private Integer categoriaId;
    private Integer produtoId;
}
