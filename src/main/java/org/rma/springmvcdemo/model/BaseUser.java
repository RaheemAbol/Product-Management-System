package org.rma.springmvcdemo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class BaseUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // Constructor
    public BaseUser() {
    }

    public BaseUser(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}