package com.example.Security_login.Model;


import com.example.Security_login.Enumerated.StatoViaggio;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Viaggio {

    @Id
    @GeneratedValue
    private int id;
    private String destinazione;
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private StatoViaggio stato;

}
