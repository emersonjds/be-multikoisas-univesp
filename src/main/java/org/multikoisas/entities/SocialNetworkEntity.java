package org.multikoisas.entities;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SocialNetworkEntity
{
    private Integer id;
    private String descricao;
    private String link;
}
