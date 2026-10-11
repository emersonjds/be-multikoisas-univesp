package org.multikoisas.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String userName;

}
