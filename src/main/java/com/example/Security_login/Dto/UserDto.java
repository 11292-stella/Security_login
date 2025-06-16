package com.example.Security_login.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDto {

    @NotEmpty(message = "il nome non puo essere vuoto")
    private String nome;
    @NotEmpty(message = "il cognome non puo essere vuoto")
    private String cognome;
    @NotEmpty(message = "username non puo essere vuoto")
    private String username;
    @NotEmpty(message = "il nome non puo essere vuoto")
    private String password;

}
