package org.multikoisas.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoImagemResponse {

    private Integer id;
    private Integer produtoId;
    private String imagemUrl;
}
