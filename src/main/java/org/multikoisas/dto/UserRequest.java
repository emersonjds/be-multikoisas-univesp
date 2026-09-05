package org.multikoisas.dto;

import lombok.*;

@Data
@Builder

public class UserRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;

}
