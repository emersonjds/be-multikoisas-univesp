package org.multikoisas.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponse {

    private Integer id;
    private String nome;
    private String descricaoProduto;
}

