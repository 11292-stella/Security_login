package com.example.Security_login.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PrenotazioneDto {
    @NotNull(message = "Id viaggio obbligatorio")
    private int viaggioId;

    @NotNull(message = "Id dipendente obbligatorio")
    private int dipendenteId;

    @NotNull(message = "Data richiesta obbligatoria")
    private LocalDate dataRichiesta;

    private String note;
}
