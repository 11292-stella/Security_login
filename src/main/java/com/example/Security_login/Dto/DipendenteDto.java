package com.example.Security_login.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DipendenteDto {

    @NotEmpty(message = "Username è obbligatorio")
    private String userName;
    @NotEmpty(message = "Il nome è obbligatorio")
    private String nome;
    @NotEmpty(message = "Il cognome è obbligatorio")
    private String cognome;
    @Email(message = "Email deve essere valida")
    private String email;


    private String immagineProfilo;



}
