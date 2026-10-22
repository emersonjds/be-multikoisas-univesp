package org.multikoisas.entities;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CanalVendaProdutoEntity {

    private Integer produtoId;
    private Integer canalVendaId;
    private String  link;
}
