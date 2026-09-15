package org.multikoisas.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario", schema = "public")

public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="\"id\"")
    private Integer id;

    @Column(name="\"firstName\"")
    private String firstName;

    @Column(name="\"lastName\"")
    private String lastName;

    @Column(name="\"email\"")
    private String email;

    @Column(name="\"password\"")
    private String password;

    @Column(name="\"username\"")
    private String username;
}
