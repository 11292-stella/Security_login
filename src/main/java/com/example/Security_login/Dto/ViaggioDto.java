package com.example.Security_login.Dto;


import com.example.Security_login.Enumerated.StatoViaggio;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ViaggioDto {
    @NotEmpty(message = "Destinazione obbligatoria")
    private String destinazione;

    @NotNull(message = "Data obbligatoria")
    private LocalDate data;

    private StatoViaggio stato;
}
