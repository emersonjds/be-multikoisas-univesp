package org.multikoisas.entities;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProdutoEntity {

    private Integer id;
    private String nome;
    private String descricaoProduto;
}
