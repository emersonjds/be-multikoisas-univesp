package org.multikoisas.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CanalVendaProdutoRequest {

    private Integer produtoId;
    private Integer canalVendaId;
    private String link;
}