package org.multikoisas.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SocialNetworkRequest
{
    private String descricao;
    private String link;
}
