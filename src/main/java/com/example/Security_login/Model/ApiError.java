package com.example.Security_login.Model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiError {

    private String message;
    private LocalDateTime dataErrore;
}
