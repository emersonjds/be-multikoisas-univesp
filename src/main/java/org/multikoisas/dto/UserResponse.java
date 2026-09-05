package org.multikoisas.dto;

import lombok.*;

@Data
@Builder

public class UserResponse {

    private String email;
    private String firstName;
    private String lastName;

}
