package com.example.Security_login.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private int id;
    private String nome;
    private String cognome;

    @Column(unique = true)
    private String username;
    private String password;
}
