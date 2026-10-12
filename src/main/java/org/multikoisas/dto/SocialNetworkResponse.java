package org.multikoisas.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class SocialNetworkResponse
{
    private Integer id;
    private String descricao;
    private String link;
}
