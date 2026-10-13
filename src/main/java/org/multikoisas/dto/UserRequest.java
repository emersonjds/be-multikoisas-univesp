package org.multikoisas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserRequest {

    @NotBlank(message = "O email é obrigatório")
    private String email;
    @NotBlank(message = "A senha é obrigatória")
    private String password;
    @NotBlank(message = "O primeiro nome é obrigatório")
    private String firstName;
    private String lastName;
    @NotBlank(message = "o nome de usuário é obrigatório")
    private String username;

}
