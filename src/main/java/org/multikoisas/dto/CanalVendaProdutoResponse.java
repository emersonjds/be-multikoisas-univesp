package org.multikoisas.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CanalVendaProdutoResponse {

    private Integer produtoId;
    private Integer canalVendaId;
    private String link;
}