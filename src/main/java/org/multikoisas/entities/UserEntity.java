package org.multikoisas.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "\"USERS\"", schema = "public")

public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id", nullable = false)
    private Long userId;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="password_hash", nullable = false)
    private String password;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;
}
